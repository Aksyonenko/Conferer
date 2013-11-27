package com.akqa.kiev.android.conferer.db;

import java.text.MessageFormat;
import java.util.Date;

import android.app.SearchManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akqa.kiev.android.conferer.model.ConferenceData;

public class ConferenceDao extends AbstractBaseDao<ConferenceData> {

	protected static final String TABLE_NAME = "conferences";

	// columns
	private static final String COLUMN_CONFERENCE_URL = "conference_url";
	private static final String COLUMN_LOGO_URL = "logo_url";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_SUMMARY = "summary";
	private static final String COLUMN_START_DATE = "start_date";
	private static final String COLUMN_END_DATE = "end_date";
	private static final String COLUMN_COUNTRY = "country";
	private static final String COLUMN_REGION = "region";
	private static final String COLUMN_CITY = "city";
	private static final String COLUMN_ADDRESS = "address";
	
	@Override
	public void init(SQLiteDatabase db) {
		mDataBase = db;
		final String createStatement = "create table " + TABLE_NAME + " (" + 
			      COLUMN_ID + " long primary key, " + 
			      COLUMN_CONFERENCE_URL + " text, " + 
			      COLUMN_LOGO_URL + " text, " + 
			      COLUMN_TITLE + " text, " + 
			      COLUMN_SUMMARY + " text, " + 
			      COLUMN_START_DATE + " long, " + 
			      COLUMN_END_DATE + " long, " + 
			      COLUMN_COUNTRY + " text, " + 
			      COLUMN_REGION + " text, " + 
			      COLUMN_CITY + " text, " + 
			      COLUMN_ADDRESS + " text); ";
		db.execSQL(createStatement);
	}
	
	public Cursor searchQuery(String searchArg) {
		final String query = MessageFormat
				.format("select {0}, {0} as {1}, {2} as {3}, {4} as {5} from {6} where {3} like ''%{7}%'';",
						COLUMN_ID, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
						COLUMN_TITLE, SearchManager.SUGGEST_COLUMN_TEXT_1,
						COLUMN_SUMMARY, SearchManager.SUGGEST_COLUMN_TEXT_2,
						TABLE_NAME, searchArg);
		return mDataBase.rawQuery(query, null);
	}
	
	
	@Override
	protected ContentValues asContentValues(ConferenceData conference) {
		ContentValues valuesMap = new ContentValues();
		valuesMap.put(COLUMN_ID, conference.getId());
		valuesMap.put(COLUMN_CONFERENCE_URL, conference.getConferenceUrl());
		valuesMap.put(COLUMN_LOGO_URL, conference.getLogoUrl());
		valuesMap.put(COLUMN_TITLE, conference.getTitle());
		valuesMap.put(COLUMN_SUMMARY, conference.getSummary());
		Long startDateInMillis = conference.getStartDate() != null ? conference
				.getStartDate().getTime() : null;
		valuesMap.put(COLUMN_START_DATE, startDateInMillis);
		Long endDateInMillis = conference.getEndDate() != null ? conference
				.getEndDate().getTime() : null;
		valuesMap.put(COLUMN_END_DATE, endDateInMillis);
		valuesMap.put(COLUMN_COUNTRY, conference.getCountry());
		valuesMap.put(COLUMN_REGION, conference.getRegion());
		valuesMap.put(COLUMN_CITY, conference.getCity());
		valuesMap.put(COLUMN_ADDRESS, conference.getAddress());
		return valuesMap;
	}
	
	@Override
	protected ConferenceData cursorToObjectInternal(Cursor cursor) {
		ConferenceData conf = new ConferenceData();
		conf.setId(cursor.getLong(0));
		conf.setConferenceUrl(cursor.getString(1));
		conf.setLogoUrl(cursor.getString(2));
		conf.setTitle(cursor.getString(3));
		conf.setSummary(cursor.getString(4));
		conf.setStartDate(new Date(cursor.getLong(5)));
		conf.setEndDate(new Date(cursor.getLong(6)));
		conf.setCountry(cursor.getString(7));
		conf.setRegion(cursor.getString(8));
		conf.setCity(cursor.getString(9));
		conf.setAddress(cursor.getString(10));
		return conf;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}


}
