
package com.akqa.kiev.conferer.server.gcm;


import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import org.mockito.runners.MockitoJUnitRunner;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Action;
import com.akqa.kiev.conferer.server.model.Action.ActionName;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gcm.server.Message;

@RunWith(MockitoJUnitRunner.class)
public class GcmSenderTest {

    @InjectMocks
    private GcmSender gcmSender = new GcmSender();

    @Mock
    private ConferenceDao conferenceDao;

    @Mock
    private SessionDao sessionDao;

    @Before
    public void before() {
        gcmSender.setObjectMapper(new ObjectMapper());
    }

    @Test
    public void testMessageConstruct() throws InterruptedException, IOException {
        Conference conf = new Conference();
        Session session1 = new Session();
        session1.setId(BigInteger.valueOf(68L));
        ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        Speaker speaker1 = new Speaker();
        speaker1.setId(BigInteger.valueOf(86L));
        speakers.add(speaker1);
        Speaker speaker2 = new Speaker();
        speaker2.setId(BigInteger.valueOf(99L));
        speakers.add(speaker2);
        Speaker speaker3 = new Speaker();
        speaker3.setId(BigInteger.valueOf(222L));
        speakers.add(speaker3);
        session1.setSpeakers(speakers);
        Session session2 = new Session();
        session2.setId(BigInteger.valueOf(54L));
        session2.setSpeakers(new ArrayList<Speaker>());
        Session session3 = new Session();
        session3.setId(BigInteger.valueOf(111L));
        session3.setSpeakers(new ArrayList<Speaker>());

        conf.setSessions(Arrays.asList(session1, session2, session3));
        when(conferenceDao.findOne(any(BigInteger.class))).thenReturn(conf);

        when(sessionDao.findOne(any(BigInteger.class))).thenReturn(session1);

        Message result = gcmSender.constructMessage(createActions());

        ObjectMapper mapper = new ObjectMapper();
        List<Action> actions = mapper.readValue(result.getData().get("DB_UPDATED"), mapper.getTypeFactory().constructCollectionType(List.class, Action.class));
        assertEquals(17, actions.size());
        assertAction(actions.get(0), ActionName.ADDED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(26L));
        assertAction(actions.get(1), ActionName.ADDED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(34L));
        assertAction(actions.get(2), ActionName.ADDED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(48L));
        assertAction(actions.get(3), ActionName.MODIFIED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(40L));
        assertAction(actions.get(4), ActionName.DELETED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(18L));
        assertAction(actions.get(5), ActionName.DELETED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(46L));
        assertAction(actions.get(6), ActionName.ADDED.name(), Session.class.getSimpleName(), BigInteger.valueOf(42L));
        assertAction(actions.get(7), ActionName.ADDED.name(), Session.class.getSimpleName(), BigInteger.valueOf(108L));
        assertAction(actions.get(8), ActionName.MODIFIED.name(), Session.class.getSimpleName(), BigInteger.valueOf(116L));
        assertAction(actions.get(9), ActionName.DELETED.name(), Session.class.getSimpleName(), BigInteger.valueOf(66L));
        assertAction(actions.get(10), ActionName.DELETED.name(), Session.class.getSimpleName(), BigInteger.valueOf(119L));
        assertAction(actions.get(11), ActionName.ADDED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(77L));
        assertAction(actions.get(12), ActionName.ADDED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(118L));
        assertAction(actions.get(13), ActionName.MODIFIED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(14L));
        assertAction(actions.get(14), ActionName.DELETED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(79L));
        assertAction(actions.get(15), ActionName.DELETED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(99L));
        assertAction(actions.get(16), ActionName.DELETED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(12L));

    }
    
    private void assertAction(Action action, String name, String entityClass, BigInteger id){
        assertEquals(action.getActionName(), name);
        assertEquals(action.getEntityClass(), entityClass);
        assertEquals(action.getEntityId(), id);
    }

    private List<Action> createActions() throws InterruptedException {
        List<Action> actions = new ArrayList<Action>();
        // added

        actions.add(new Action(ActionName.ADDED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(26L)));
        actions.add(new Action(ActionName.ADDED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(34L)));
        actions.add(new Action(ActionName.ADDED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(48L)));

        actions.add(new Action(ActionName.ADDED.name(), Session.class.getSimpleName(), BigInteger.valueOf(42L)));
        // should be removed as we have added session that contains session with id 68
        actions.add(new Action(ActionName.ADDED.name(), Session.class.getSimpleName(), BigInteger.valueOf(68L)));
        actions.add(new Action(ActionName.ADDED.name(), Session.class.getSimpleName(), BigInteger.valueOf(108L)));

        actions.add(new Action(ActionName.ADDED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(77L)));
        // should be removed as we already have ADDED speaker with id 77
        actions.add(new Action(ActionName.ADDED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(77L)));
        actions.add(new Action(ActionName.ADDED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(118L)));
        Thread.sleep(1000);

        // modified

        // should be removed as we have DELETED conference with id 18
        actions.add(new Action(ActionName.MODIFIED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(18L)));
        // should be removed as we have ADDED conference with id 34
        actions.add(new Action(ActionName.MODIFIED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(34L)));
        actions.add(new Action(ActionName.MODIFIED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(40L)));

        // should be removed as we have ADDED session with id 42
        actions.add(new Action(ActionName.MODIFIED.name(), Session.class.getSimpleName(), BigInteger.valueOf(42L)));
        // should be removed as we have added session that contains session with id 54
        actions.add(new Action(ActionName.MODIFIED.name(), Session.class.getSimpleName(), BigInteger.valueOf(54L)));
        actions.add(new Action(ActionName.MODIFIED.name(), Session.class.getSimpleName(), BigInteger.valueOf(116L)));

        // should be removed as we have ADDED speaker with id 77
        actions.add(new Action(ActionName.MODIFIED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(77L)));
        // should be removed as we have modified session that contains speaker with id 86
        actions.add(new Action(ActionName.MODIFIED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(86L)));
        actions.add(new Action(ActionName.MODIFIED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(14L)));
        Thread.sleep(1000);

        // deleted

        actions.add(new Action(ActionName.DELETED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(18L)));
        actions.add(new Action(ActionName.DELETED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(46L)));
        // should be removed as we have ADDED conference with id 48 last action
        actions.add(new Action(ActionName.DELETED.name(), Conference.class.getSimpleName(), BigInteger.valueOf(48L)));

        // should be removed as we have ADDED session with id 42 last action
        actions.add(new Action(ActionName.DELETED.name(), Session.class.getSimpleName(), BigInteger.valueOf(42L)));
        actions.add(new Action(ActionName.DELETED.name(), Session.class.getSimpleName(), BigInteger.valueOf(66L)));
        actions.add(new Action(ActionName.DELETED.name(), Session.class.getSimpleName(), BigInteger.valueOf(119L)));

        actions.add(new Action(ActionName.DELETED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(79L)));
        actions.add(new Action(ActionName.DELETED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(99L)));
        actions.add(new Action(ActionName.DELETED.name(), Speaker.class.getSimpleName(), BigInteger.valueOf(12L)));
        return actions;
    }

}
