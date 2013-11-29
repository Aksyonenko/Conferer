package com.akqa.kiev.android.conferer.db;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.SearchManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

	private static final int SEARCH_LIMIT = 3;
	protected static final String COLUMN_ID = BaseColumns._ID;

	protected SQLiteDatabase mDataBase;

	@Override
	public T getById(Long id) {
		Cursor cursor = mDataBase.rawQuery(String.format(
				"select * from %s where %s = ?", getTableName(), COLUMN_ID),
				new String[] { String.valueOf(id) });
		return cursorToObject(cursor);
	}

	@Override
	public List<T> findAll() {
		Cursor cursor = mDataBase.rawQuery(
				String.format("select * from %s", getTableName()), null);
		List<T> result = new ArrayList<T>();
		try {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					result.add(cursorToObjectInternal(cursor));
					cursor.moveToNext();
				}
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	@Override
	public void insert(T item) {
		mDataBase.insert(getTableName(), null, asContentValues(item));
	}

	protected void setDataBase(SQLiteDatabase mDataBase) {
		this.mDataBase = mDataBase;
	}

	@Override
	public void insertList(List<T> items) {
		for (T item : items) {
			insert(item);
		}
	}
	
	public Cursor searchQuery(String searchArg) {
		Map<String, String> map = getSearchColumnsMap();
		final String query = MessageFormat
				.format("select {0}, {0} as {1}, {2} as {3}, {4} as {5}, {6} as {7}, {8} as {9} from {10} where {3} like ''%{11}%'' order by {3} limit {12};",
						COLUMN_ID, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
						map.get(SearchManager.SUGGEST_COLUMN_TEXT_1), SearchManager.SUGGEST_COLUMN_TEXT_1,
						map.get(SearchManager.SUGGEST_COLUMN_TEXT_2), SearchManager.SUGGEST_COLUMN_TEXT_2,
						map.get(SearchManager.SUGGEST_COLUMN_ICON_1), SearchManager.SUGGEST_COLUMN_ICON_1,
						"'" + getTableName() + "'", SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA,
						getTableName(), searchArg, SEARCH_LIMIT);
		return mDataBase.rawQuery(query, null);
	}
	
	protected T cursorToObject(Cursor cursor) {
		try {
			if (cursor.moveToFirst()) {
				return cursorToObjectInternal(cursor);
			} else {
				return null;
			}
		} finally {
			cursor.close();
		}
	}
	
	protected List<T> cursorToListObjects(Cursor cursor) {
		List<T> list = new ArrayList<T>();
		try {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					list.add(cursorToObjectInternal(cursor));
					cursor.moveToNext();
				}
			}
		} finally {
			cursor.close();
		}
		return list;
	}
	
	public abstract String getTableName();

	protected abstract T cursorToObjectInternal(Cursor cursor);

	protected abstract ContentValues asContentValues(T item);
	
	protected abstract Map<String, String> getSearchColumnsMap();

}
