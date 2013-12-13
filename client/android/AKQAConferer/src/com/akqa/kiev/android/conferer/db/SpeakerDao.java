package com.akqa.kiev.android.conferer.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.SearchManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SearchData;
import com.akqa.kiev.android.conferer.model.SocialLinksData;
import com.akqa.kiev.android.conferer.model.SpeakerData;

public class SpeakerDao extends AbstractBaseDao<SpeakerData> {
	
	public static final String TABLE_NAME = "speakers";

	// columns
	private static final String COLUMN_SPEAKER_URL = "speaker_url";
	private static final String COLUMN_FIRSTNAME = "firstname";
	private static final String COLUMN_LASTNAME = "lastname";
	private static final String COLUMN_COMPETENCE = "competence";
	private static final String COLUMN_PHOTO_URL = "photo_url";
	private static final String COLUMN_ABOUT = "about";
	private static final String COLUMN_FACEBOOK= "facebook";
	private static final String COLUMN_TWITTER = "twitter";
	private static final String COLUMN_LINKEDIN = "linkedin";

	@Override
	public void init(SQLiteDatabase db) {
		mDataBase = db;
		final String createStatement = "create table " + TABLE_NAME + " (" + 
			      COLUMN_ID + " long primary key, " + 
			      COLUMN_SPEAKER_URL + " text, " + 
			      COLUMN_FIRSTNAME + " text, " + 
			      COLUMN_LASTNAME + " text, " + 
			      COLUMN_COMPETENCE + " text, " + 
			      COLUMN_PHOTO_URL + " text, " + 
			      COLUMN_ABOUT + " text, " + 
			      COLUMN_FACEBOOK + " text, " + 
			      COLUMN_TWITTER + " text, " + 
			      COLUMN_LINKEDIN + " text); ";
		db.execSQL(createStatement);
	}
	
	public List<SpeakerData> getList(List<Long> ids) {
		List<SpeakerData> speakers = new ArrayList<SpeakerData>();
		String inClause = ids.toString().replace("[", "").replace("]", "");
		Cursor cursor = mDataBase.rawQuery(String.format(
				"select * from %s where %s in (%s)", TABLE_NAME, COLUMN_ID,
				inClause), null);
		try {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					speakers.add(cursorToObjectInternal(cursor));
					cursor.moveToNext();
				}
			}
		} finally {
			cursor.close();
		}
		return speakers;
	}
	
	public List<SpeakerData> getList(Long... ids){	
		return getList(Arrays.asList(ids));
	}
	
	@Override
	protected Map<String, String> getSearchColumnsMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_1, "(" + COLUMN_FIRSTNAME
				+ "||' '||" + COLUMN_LASTNAME + ")"); // concatenation firstname
														// and lastname in sql
														// query
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_2, COLUMN_COMPETENCE);
		map.put(SearchManager.SUGGEST_COLUMN_ICON_1,
				String.valueOf(R.drawable.search_speaker));
		return map;
	}
	
	@Override
	protected ContentValues asContentValues(SpeakerData speaker) {
		ContentValues valuesMap = new ContentValues();
		valuesMap.put(COLUMN_ID, speaker.getId());
		valuesMap.put(COLUMN_SPEAKER_URL, speaker.getSpeakerUrl());
		valuesMap.put(COLUMN_FIRSTNAME, speaker.getFirstName());
		valuesMap.put(COLUMN_LASTNAME, speaker.getLastName());
		valuesMap.put(COLUMN_COMPETENCE, speaker.getCompetence());
		valuesMap.put(COLUMN_PHOTO_URL, speaker.getPhotoUrl());
		valuesMap.put(COLUMN_ABOUT, speaker.getAbout());
		valuesMap.put(COLUMN_FACEBOOK, speaker.getSocialLinks().getFacebook());
		valuesMap.put(COLUMN_TWITTER, speaker.getSocialLinks().getTwitter());
		valuesMap.put(COLUMN_LINKEDIN, speaker.getSocialLinks().getLinkedin());
		return valuesMap;
	}
	
	@Override
	protected SpeakerData cursorToObjectInternal(Cursor cursor) {
		SpeakerData speaker = new SpeakerData();
		speaker.setId(cursor.getLong(0));
		speaker.setSpeakerUrl(cursor.getString(1));
		speaker.setFirstName(cursor.getString(2));
		speaker.setLastName(cursor.getString(3));
		speaker.setCompetence(cursor.getString(4));
		speaker.setPhotoUrl(cursor.getString(5));
		speaker.setAbout(cursor.getString(6));
		SocialLinksData socialLinks = new SocialLinksData();
		socialLinks.setFacebook(cursor.getString(7));
		socialLinks.setTwitter(cursor.getString(8));
		socialLinks.setLinkedin(cursor.getString(9));
		speaker.setSocialLinks(socialLinks);
		return speaker;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public SearchData cursorToSearchObject(Cursor cursor) {
		SearchData data = new SearchData();
		data.setType(SearchData.TYPE_SPEAKER);
		data.setId(cursor.getLong(0));
		data.setTitle(cursor.getString(2));
		data.setSubtitle(cursor.getString(3));
		return data;
	}

}
