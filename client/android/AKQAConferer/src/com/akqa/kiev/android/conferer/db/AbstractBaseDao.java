package com.akqa.kiev.android.conferer.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

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
	
	public abstract String getTableName();

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

	protected abstract T cursorToObjectInternal(Cursor cursor);

	protected abstract ContentValues asContentValues(T item);

}
