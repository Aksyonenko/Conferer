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
import android.database.Cursor;

import com.akqa.kiev.android.conferer.db.CategoryDao;
import com.akqa.kiev.android.conferer.db.ConferenceDao;
import com.akqa.kiev.android.conferer.db.ConfererDatabase;
import com.akqa.kiev.android.conferer.db.SessionDao;
import com.akqa.kiev.android.conferer.db.SpeakerDao;
import com.akqa.kiev.android.conferer.model.CategoryData;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SearchData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.utils.DateUtils;

public class ConfererDbService implements ConfererService {
	
    private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
		
	private ConferenceDao conferenceDao;
	private SessionDao sessionDao;
	private SpeakerDao speakerDao;
	private CategoryDao categoryDao;
	
	public ConfererDbService(Context context) {
		conferenceDao = ConfererDatabase.getInstance(context)
				.getConferenceDao();
		sessionDao = ConfererDatabase.getInstance(context).getSessionDao();
		speakerDao = ConfererDatabase.getInstance(context).getSpeakerDao();
		categoryDao = ConfererDatabase.getInstance(context).getCategoryDao();
	}

	@Override
	public List<Long> loadConferencesMonths() {
		Set<Long> dates = new HashSet<Long>();
		List<ConferenceData> allConferences = conferenceDao.findAll();
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
		List<ConferenceData> allConferences = conferenceDao.findAll();

		List<ConferenceData> monthConf = new ArrayList<ConferenceData>();

		for (ConferenceData conference : allConferences) {
			if (DateUtils.between(from, to, conference.getStartDate(),
					conference.getEndDate())) {
				monthConf.add(conference);
			}
		}
		return monthConf;
	}
	
	public List<ConferenceData> loadConferencesForCategory(long categoryId) {
		return conferenceDao.getByCategory(categoryId);
	}

	@Override
	public ConferenceDetailsData loadConferenceDetails(long id) {
		ConferenceData conference = conferenceDao.getById(id);
		ConferenceDetailsData conferenceDetails = new ConferenceDetailsData(
				conference);
		List<SessionData> sessions = sessionDao.getConferenceSessions(id);
		for (SessionData session : sessions) {
			enrichSession(session);
		}
		conferenceDetails.setSessions(sessions);
		return conferenceDetails;
	}

	@Override
	public SessionData loadSessionDetails(long id) {
		SessionData session = sessionDao.getById(id);
		enrichSession(session);
		return session;
	}
	
	public Long getConferenceId(Long sessionId) {
		return sessionDao.getConferenceId(sessionId);
	}
	
	public List<SearchData> searchConferences(String searchArg) {
		List<SearchData> searchDataList = new ArrayList<SearchData>();
		Cursor searchResult = conferenceDao.searchQuery(searchArg);
		if(searchResult.moveToFirst()) {
			do {
				searchDataList.add(conferenceDao.cursorToSearchObject(searchResult));
			} while(searchResult.moveToNext());
		}
		searchResult.close();
		return searchDataList;
	}
	
	public List<SearchData> searchSessions(String searchArg) {
		List<SearchData> searchDataList = new ArrayList<SearchData>();
		Cursor searchResult = sessionDao.searchQuery(searchArg);
		if(searchResult.moveToFirst()) {
			do {
				searchDataList.add(sessionDao.cursorToSearchObject(searchResult));
			} while(searchResult.moveToNext());
		}
		searchResult.close();
		return searchDataList;
	}
	
	public List<SearchData> searchSpeakers(String searchArg) {
		List<SearchData> searchDataList = new ArrayList<SearchData>();
		Cursor searchResult = speakerDao.searchQuery(searchArg);
		if(searchResult.moveToFirst()) {
			do {
				searchDataList.add(speakerDao.cursorToSearchObject(searchResult));
			} while(searchResult.moveToNext());
		}
		searchResult.close();
		return searchDataList;
	}
	
	
	private void enrichSession(SessionData session) {
		List<Long> speakersIds = sessionDao.getSpeakerIdsForSession(session
				.getId());
		List<SpeakerData> speakers = speakerDao.getList(speakersIds);
		session.setSpeakers(speakers);
	}

	@Override
	public SpeakerData loadSpeakerDetails(long id) {
		return speakerDao.getById(id);
	}
	
	public List<CategoryData> loadCategories() {
		return categoryDao.findAll();
	}
	
	public CategoryData loadCategory(long id) {
		return categoryDao.getById(id);
	}

}
