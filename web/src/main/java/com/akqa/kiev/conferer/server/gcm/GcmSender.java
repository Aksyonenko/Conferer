
package com.akqa.kiev.conferer.server.gcm;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.akqa.kiev.conferer.server.dao.ActionDao;
import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.dao.GcmRegistrationDao;
import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Action;
import com.akqa.kiev.conferer.server.model.Action.ActionName;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.GcmRegistration;
import com.akqa.kiev.conferer.server.model.QAction;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.common.collect.Lists;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

@Component
public class GcmSender {

    private static Logger LOG = LoggerFactory.getLogger(GcmSender.class);

    @Value("${gcm.multicast.max.size}")
    private int multicastMaxSize;

    @Value("${gcm.sender.retries}")
    private int senderRetries;

    @Autowired
    private Sender sender;

    @Autowired
    private GcmRegistrationDao gcmRegistrationDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private ConferenceDao conferenceDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Executor THREAD_POOL = Executors.newFixedThreadPool(5);

    public void sendNotification() throws IOException {

        Predicate predicate = QAction.action.sentToDevice.isFalse();
        OrderSpecifier<Date> sortOrder = QAction.action.dbTimestamp.desc();
        Iterable<Action> actions = actionDao.findAll(predicate, sortOrder);

        if (actions == null || !actions.iterator().hasNext()) {
            return;
        }
        Message message = constructMessage(actions);

        Iterable<GcmRegistration> devicesIterable = gcmRegistrationDao.findAll();
        ArrayList<GcmRegistration> devices = Lists.newArrayList(devicesIterable);
        if (devices.isEmpty()) {
            LOG.info("Message ignored as there is no device registered!");
        } else {
            int total = Math.min(devices.size(), multicastMaxSize);
            List<String> partialDevices = new ArrayList<String>(total);
            int counter = 0;
            int tasks = 0;
            for (GcmRegistration device : devices) {
                counter++;
                partialDevices.add(device.getRegistrationId());
                int partialSize = partialDevices.size();
                if (partialSize == multicastMaxSize || counter == total) {
                    asyncSend(partialDevices, message);
                    partialDevices.clear();
                    tasks++;
                }
            }
            LOG.info("Asynchronously sending " + tasks + " multicast messages to " + total + " devices");
        }

        for (Action action : actions) {
            action.setSentToDevice(true);
        }
        actionDao.save(actions);
    }

    private void asyncSend(List<String> partialDevices, final Message message) {

        final List<String> devices = new ArrayList<String>(partialDevices);

        THREAD_POOL.execute(new Runnable() {

            public void run() {
                MulticastResult multicastResult;
                try {
                    multicastResult = sender.send(message, devices, senderRetries);
                } catch (IOException e) {
                    LOG.error("Error posting messages", e);
                    return;
                }
                List<Result> results = multicastResult.getResults();

                for (int i = 0; i < devices.size(); i++) {
                    String regId = devices.get(i);
                    Result result = results.get(i);
                    String messageId = result.getMessageId();
                    if (messageId != null) {
                        LOG.info("Succesfully sent message to device: " + regId + "; messageId = " + messageId);
                        String canonicalRegId = result.getCanonicalRegistrationId();

                        if (canonicalRegId != null) {
                            // same device has more than one registration id - update it
                            LOG.info("canonicalRegId " + canonicalRegId);
                            gcmRegistrationDao.delete(regId);
                            gcmRegistrationDao.save(new GcmRegistration(canonicalRegId));
                        }

                    } else {
                        String error = result.getErrorCodeName();
                        if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                            // application has been removed from device - unregister it
                            LOG.info("Unregistered device: " + regId);
                            gcmRegistrationDao.delete(regId);
                        } else if (error.equals(Constants.ERROR_INVALID_REGISTRATION)) {
                            LOG.info("Invalid registration: " + regId);
                            gcmRegistrationDao.delete(regId);
                        } else {
                            LOG.error("Error sending message to " + regId + ": " + error);
                        }
                    }
                }
            }
        });
    }

    protected Message constructMessage(Iterable<Action> actions) throws JsonProcessingException {
        List<Action> conferencesActions = new ArrayList<Action>();
        List<Action> sessionsActions = new ArrayList<Action>();
        List<Action> speakersActions = new ArrayList<Action>();
        for (Action action : actions) {
            if (action.getEntityClass().equals(Conference.class.getSimpleName())) {
                conferencesActions.add(action);
            } else if (action.getEntityClass().equals(Session.class.getSimpleName())) {
                sessionsActions.add(action);
            } else if (action.getEntityClass().equals(Speaker.class.getSimpleName())) {
                speakersActions.add(action);
            }
        }

        conferencesActions = removeUnnecessaryActions(conferencesActions);

        sessionsActions = removeUnnecessaryActions(sessionsActions);
        sessionsActions = removeUnnecessarySessions(sessionsActions, conferencesActions);

        speakersActions = removeUnnecessaryActions(speakersActions);
        speakersActions = removeUnnecessarySpeakers(speakersActions, conferencesActions, sessionsActions);

        Message.Builder messageBuilder = new Message.Builder();

        conferencesActions.addAll(sessionsActions);
        conferencesActions.addAll(speakersActions);
        messageBuilder.addData("DB_UPDATED", objectMapper.writeValueAsString(conferencesActions));

        return messageBuilder.build();
    }

    private List<Action> removeUnnecessaryActions(List<Action> actions) {
        List<BigInteger> added = new ArrayList<BigInteger>();
        List<BigInteger> deleted = new ArrayList<BigInteger>();

        for (Iterator<Action> iterator = actions.iterator(); iterator.hasNext();) {
            Action action = iterator.next();
            BigInteger entityId = action.getEntityId();
            String actionName = action.getActionName();
            if (actionName.equals(ActionName.ADDED.name())) {
                if (!added.contains(entityId) && !deleted.contains(entityId)) {
                    added.add(entityId);
                } else {
                    iterator.remove();
                }
            } else if (actionName.equals(ActionName.DELETED.name())) {
                if (!added.contains(entityId) && !deleted.contains(entityId)) {
                    deleted.add(entityId);
                } else {
                    iterator.remove();
                }
            }
        }

        for (Iterator<Action> iterator = actions.iterator(); iterator.hasNext();) {
            Action action = iterator.next();
            BigInteger entityId = action.getEntityId();
            if (action.getActionName().equals(ActionName.MODIFIED.name())) {
                if (added.contains(entityId) || deleted.contains(entityId)) {
                    iterator.remove();
                }
            }
        }

        return actions;
    }

    private List<Action> removeUnnecessarySessions(List<Action> sessionsActions, List<Action> conferencesActions) {
        for (Action conferenceAction : conferencesActions) {
            if (!conferenceAction.getActionName().equals(ActionName.MODIFIED.name())) {
                BigInteger conferenceId = conferenceAction.getEntityId();
                Conference conference = conferenceDao.findOne(conferenceId);
                for (Session session : conference.getSessions()) {
                    for (Iterator<Action> iterator = sessionsActions.iterator(); iterator.hasNext();) {
                        Action sessionAction = iterator.next();
                        if (sessionAction.getEntityId().equals(session.getId())) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        return sessionsActions;
    }

    private List<Action> removeUnnecessarySpeakers(List<Action> speakersActions, List<Action> conferencesActions,
            List<Action> sessionsActions) {
        for (Action conferenceAction : conferencesActions) {
            if (!conferenceAction.getActionName().equals(ActionName.MODIFIED.name())) {
                BigInteger conferenceId = conferenceAction.getEntityId();
                Conference conference = conferenceDao.findOne(conferenceId);
                for (Session session : conference.getSessions()) {
                    removeUnnecessarySpeakersFromSession(session, speakersActions);
                }
            }
        }

        for (Action sessionAction : sessionsActions) {
            if (!sessionAction.getActionName().equals(ActionName.DELETED.name())) {
                BigInteger sessionId = sessionAction.getEntityId();
                Session session = sessionDao.findOne(sessionId);
                removeUnnecessarySpeakersFromSession(session, speakersActions);
            }
        }
        return speakersActions;
    }
    
    private void removeUnnecessarySpeakersFromSession(Session session, List<Action> speakersActions){
        for (Speaker speaker : session.getSpeakers()) {
            for (Iterator<Action> iterator = speakersActions.iterator(); iterator.hasNext();) {
                Action speakerAction = iterator.next();
                if (!speakerAction.getActionName().equals(ActionName.DELETED.name())
                        && speakerAction.getEntityId().equals(speaker.getId())) {
                    iterator.remove();
                }
            }
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
