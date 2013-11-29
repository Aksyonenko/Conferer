package com.akqa.kiev.android.conferer;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;

import com.akqa.kiev.android.conferer.db.ConferenceDao;
import com.akqa.kiev.android.conferer.db.ConfererDatabase;
import com.akqa.kiev.android.conferer.db.SessionDao;
import com.akqa.kiev.android.conferer.db.SpeakerDao;

public class SearchSuggestionProvider extends ContentProvider {

	public static String AUTHORITY = "com.akqa.kiev.android.conferer.SearchSuggestionProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/conferer");

	private ConferenceDao conferenceDao;
	private SessionDao sessionDao;
	private SpeakerDao speakerDao;

	@Override
	public boolean onCreate() {
		Context context = getContext();
		conferenceDao = ConfererDatabase.getInstance(context)
				.getConferenceDao();
		sessionDao = ConfererDatabase.getInstance(context).getSessionDao();
		speakerDao = ConfererDatabase.getInstance(context).getSpeakerDao();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (selectionArgs == null) {
			throw new IllegalArgumentException(
					"selectionArgs must be provided for the Uri: " + uri);
		}
		Cursor conferencesCursor = conferenceDao.searchQuery(selectionArgs[0]);
		Cursor sessionsCursor = sessionDao.searchQuery(selectionArgs[0]);
		Cursor speakersCursor = speakerDao.searchQuery(selectionArgs[0]);

		MergeCursor mergedCursor = new MergeCursor(new Cursor[] {
				conferencesCursor, sessionsCursor, speakersCursor });

		if (!mergedCursor.moveToFirst()) {
			mergedCursor.close();
			return null;
		}
		return mergedCursor;
	}

	@Override
	public String getType(Uri uri) {
		return SearchManager.SUGGEST_MIME_TYPE;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
}
