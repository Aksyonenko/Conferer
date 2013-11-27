package com.akqa.kiev.android.conferer.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import android.content.Context;

import com.akqa.kiev.android.conferer.db.ConfererDatabase;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.utils.DateUtils;

public class ConfererDbService implements ConfererService {
	
    private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
	
	private ConfererDatabase confererDatabase;
	
	public ConfererDbService(Context context) {
		confererDatabase = new ConfererDatabase(context);
	}

	@Override
	public List<Long> loadConferencesMonths() {
		Set<Long> dates = new HashSet<Long>();
		List<ConferenceData> allConferences = confererDatabase
				.getConferenceDao().findAll();
		for (ConferenceData conference : allConferences) {
			Calendar startCalendar = Calendar.getInstance(UTC_TZ);
			startCalendar.setTime((Date) conference.getStartDate().clone());
			
			Calendar endCalendar = Calendar.getInstance(UTC_TZ);
			endCalendar.setTime((Date) conference.getEndDate().clone());

			startCalendar.setTimeZone(UTC_TZ);
			endCalendar.setTimeZone(UTC_TZ);

			startCalendar.set(Calendar.DAY_OF_MONTH, 1);

			for (int field : new int[] { Calendar.HOUR_OF_DAY, Calendar.MINUTE,
					Calendar.SECOND, Calendar.MILLISECOND }) {
				startCalendar.set(field, 0);
			}

			do {
				dates.add(startCalendar.getTimeInMillis());
				startCalendar.add(Calendar.MONTH, 1);
			} while (startCalendar.before(endCalendar));
		}

		List<Long> sortedDates = new ArrayList<Long>(dates);
		Collections.sort(sortedDates);

		return sortedDates;
	}

	@Override
	public List<ConferenceData> loadConferencesForMonth(long date) {
		
		Calendar calendar = Calendar.getInstance(UTC_TZ);
		calendar.setTimeInMillis(date);
		
		Date from = calendar.getTime();

		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);

		Date to = calendar.getTime();
		List<ConferenceData> allConferences = confererDatabase
				.getConferenceDao().findAll();

		List<ConferenceData> monthConf = new ArrayList<ConferenceData>();

		for (ConferenceData conference : allConferences) {
			if (DateUtils.between(from, to, conference.getStartDate(),
					conference.getEndDate())) {
				monthConf.add(conference);
			}
		}
		return monthConf;
	}

	@Override
	public ConferenceDetailsData loadConferenceDetails(long id) {
		ConferenceData conference = confererDatabase.getConferenceDao()
				.getById(id);
		ConferenceDetailsData conferenceDetails = new ConferenceDetailsData(
				conference);
		List<SessionData> sessions = confererDatabase.getSessionDao()
				.getConferenceSessions(id);
		for (SessionData session : sessions) {
			enrichSession(session);
		}
		conferenceDetails.setSessions(sessions);
		return conferenceDetails;
	}

	@Override
	public SessionData loadSessionDetails(long id) {
		SessionData session = confererDatabase.getSessionDao().getById(id);
		enrichSession(session);
		return session;
	}
	
	private void enrichSession(SessionData session){
		List<Long> speakersIds = confererDatabase.getSessionDao()
				.getSpeakerIdsForSession(session.getId());
		List<SpeakerData> speakers = confererDatabase.getSpeakerDao().getList(
				speakersIds);
		session.setSpeakers(speakers);
	}

	@Override
	public SpeakerData loadSpeakereDetails(long id) {
		return confererDatabase.getSpeakerDao().getById(id);
	}
	
	

}
