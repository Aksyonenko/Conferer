package com.akqa.kiev.android.conferer;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.akqa.kiev.android.conferer.db.ConfererDatabase;

public class SearchSuggestionProvider extends ContentProvider {

	public static String AUTHORITY = "com.akqa.kiev.android.conferer.SearchSuggestionProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/conferences");

	
	private ConfererDatabase confererDatabase;

	@Override
	public boolean onCreate() {
		confererDatabase = new ConfererDatabase(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (selectionArgs == null) {
			throw new IllegalArgumentException(
					"selectionArgs must be provided for the Uri: " + uri);
		}
		Cursor cursor = confererDatabase.getConferenceDao().searchQuery(selectionArgs[0]);
		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
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
