package com.akqa.kiev.android.conferer.db;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.SearchManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SearchData;
import com.akqa.kiev.android.conferer.model.SessionData;

public class SessionDao extends AbstractBaseDao<SessionData> {

	public static final String TABLE_NAME = "sessions";
	

	// columns
	private static final String COLUMN_CONFERENCE_ID = "conference_id";//foreign key for conference table
	private static final String COLUMN_SESSION_URL = "session_url";
	private static final String COLUMN_SESSION_LOGO_URL = "session_logo_url";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_TYPE = "type";
	private static final String COLUMN_SUMMARY = "summary";
	private static final String COLUMN_START_TIME = "start_time";
	private static final String COLUMN_END_TIME = "end_time";
	private static final String COLUMN_DETAILS = "details";
	private static final String COLUMN_LOCATION = "location";
	
	private static final String SESSION_SPEAKER_TABLE_NAME = "session_speaker";
	private static final String COLUMN_SESSION_ID = "session_id";
	private static final String COLUMN_SPEAKER_ID = "speaker_id";


	@Override
	public void init(SQLiteDatabase db) {	
		mDataBase = db;
		final String createStatement = "create table " + TABLE_NAME + " (" + 
			      COLUMN_ID + " long primary key, " + 
			      COLUMN_SESSION_URL + " text, " + 
			      COLUMN_SESSION_LOGO_URL + " text, " + 
			      COLUMN_TITLE + " text, " + 
			      COLUMN_TYPE + " text, " + 
			      COLUMN_SUMMARY + " text, " + 
			      COLUMN_START_TIME + " long, " + 
			      COLUMN_END_TIME + " long, " + 
			      COLUMN_DETAILS + " text, " + 
			      COLUMN_LOCATION + " text, "+
			      COLUMN_CONFERENCE_ID + " long, " + 
			      "foreign key(" + COLUMN_CONFERENCE_ID + ") references "+ ConferenceDao.TABLE_NAME + "(" + COLUMN_ID + "));";
		db.execSQL(createStatement);
		
		final String createSessionSpeakerStatement = "create table " + SESSION_SPEAKER_TABLE_NAME + " (" + 
			      COLUMN_SESSION_ID + " long, " + 
			      COLUMN_SPEAKER_ID + " long, " + 
			      "primary key (" + COLUMN_SESSION_ID + "," + COLUMN_SPEAKER_ID + "), " +
			      "foreign key(" + COLUMN_SESSION_ID + ") references "+ TABLE_NAME + "(" + COLUMN_ID + "), "+
			      "foreign key(" + COLUMN_SPEAKER_ID + ") references "+ SpeakerDao.TABLE_NAME + "(" + COLUMN_ID + "));";
		db.execSQL(createSessionSpeakerStatement);
	}
	
	public void assignSpeakerToSession(Long sessionId, Long speakerId) {
		ContentValues valuesMap = new ContentValues();
		valuesMap.put(COLUMN_SESSION_ID, sessionId);
		valuesMap.put(COLUMN_SPEAKER_ID, speakerId);
		mDataBase.insert(SESSION_SPEAKER_TABLE_NAME, null, valuesMap);
	}
	
	public List<Long> getSpeakerIdsForSession(Long sessionId) {
		Cursor cursor = mDataBase.rawQuery(String.format(
				"select %s from %s where %s = ?", COLUMN_SPEAKER_ID,
				SESSION_SPEAKER_TABLE_NAME, COLUMN_SESSION_ID), new String[] { String
				.valueOf(sessionId) });
		List<Long> ids = new ArrayList<Long>();
		try {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					ids.add(cursor.getLong(0));
					cursor.moveToNext();
				}
			}
		} finally {
			cursor.close();
		}
		return ids;
	}
	
	public List<SessionData> getConferenceSessions(Long confId) {
		List<SessionData> sessions = new ArrayList<SessionData>();
		Cursor cursor = mDataBase.rawQuery(String.format(
				"select * from %s where %s = ?", TABLE_NAME,
				COLUMN_CONFERENCE_ID), new String[] { String.valueOf(confId) });
		try {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					sessions.add(cursorToObjectInternal(cursor));
					cursor.moveToNext();
				}
			}
		} finally {
			cursor.close();
		}
		return sessions;
	}
	
	 public Long getConferenceId(long sessionId) {
		  Cursor cursor = mDataBase.rawQuery(MessageFormat.format(
		    "select {0} from {1} where {2} = ?", COLUMN_CONFERENCE_ID,
		    TABLE_NAME, COLUMN_ID), new String[] { String
		    .valueOf(sessionId) });
		  try {
		   if (cursor.moveToFirst()) {
		    return cursor.getLong(0);
		   } else {
		    return null;
		   }
		  } finally {
		   cursor.close();
		  }
		 }
	
	
	public void insert(SessionData session, Long conferenceId) {
		ContentValues valuesMap = asContentValues(session);
		valuesMap.put(COLUMN_CONFERENCE_ID, conferenceId);
		mDataBase.insert(TABLE_NAME, null, valuesMap);
	}
	
	@Override
	protected Map<String, String> getSearchColumnsMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_1, COLUMN_TITLE);
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_2, COLUMN_SUMMARY);
		map.put(SearchManager.SUGGEST_COLUMN_ICON_1,
				String.valueOf(R.drawable.search_session));
		return map;
	}
	
	@Override
	protected ContentValues asContentValues(SessionData session) {
		ContentValues valuesMap = new ContentValues();
		valuesMap.put(COLUMN_ID, session.getId());
		valuesMap.put(COLUMN_SESSION_URL, session.getSessionUrl());
		valuesMap.put(COLUMN_SESSION_LOGO_URL, session.getSessionLogoUrl());
		valuesMap.put(COLUMN_TITLE, session.getTitle());
		valuesMap.put(COLUMN_TYPE, session.getType());
		valuesMap.put(COLUMN_SUMMARY, session.getSummary());
		Long startTimeInMillis = session.getStartTime() != null ? session
				.getStartTime().getTime() : null;
		valuesMap.put(COLUMN_START_TIME, startTimeInMillis);
		Long endTimeInMillis = session.getEndTime() != null ? session
				.getEndTime().getTime() : null;
		valuesMap.put(COLUMN_END_TIME, endTimeInMillis);
		valuesMap.put(COLUMN_DETAILS, session.getDetails());
		valuesMap.put(COLUMN_LOCATION, session.getLocation());
		return valuesMap;
	}

	@Override
	protected SessionData cursorToObjectInternal(Cursor cursor) {
		SessionData session = new SessionData();
		session.setId(cursor.getLong(0));
		session.setSessionUrl(cursor.getString(1));
		session.setSessionLogoUrl(cursor.getString(2));
		session.setTitle(cursor.getString(3));
		session.setType(cursor.getString(4));
		session.setSummary(cursor.getString(5));
		session.setStartTime(new Date(cursor.getLong(6)));
		session.setEndTime(new Date(cursor.getLong(7)));
		session.setDetails(cursor.getString(8));
		session.setLocation(cursor.getString(9));
		return session;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public SearchData cursorToSearchObject(Cursor cursor) {
		SearchData data = new SearchData();
		data.setType(SearchData.TYPE_SESSION);
		data.setId(cursor.getLong(0));
		data.setTitle(cursor.getString(2));
		data.setSubtitle(cursor.getString(3));
		return data;
	}


}
