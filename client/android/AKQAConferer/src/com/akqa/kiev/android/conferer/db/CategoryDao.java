package com.akqa.kiev.android.conferer.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akqa.kiev.android.conferer.model.CategoryData;
import com.akqa.kiev.android.conferer.model.SearchData;

public class CategoryDao extends AbstractBaseDao<CategoryData> {

	public static final String TABLE_NAME = "categories";
	public static final String COLUMN_NAME = "name";
	
	
	@Override
	public void init(SQLiteDatabase db) {
		mDataBase = db;
		final String createStatement = "create table " + TABLE_NAME + "(" +
				COLUMN_ID + " long primary key, " +
				COLUMN_NAME + " text);";
		db.execSQL(createStatement);
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected CategoryData cursorToObjectInternal(Cursor cursor) {
		CategoryData data = new CategoryData();
		data.setId(cursor.getLong(0));
		data.setName(cursor.getString(1));
		return data;
	}

	@Override
	protected ContentValues asContentValues(CategoryData item) {
		ContentValues valuesMap = new ContentValues();
		valuesMap.put(COLUMN_ID, item.getId());
		valuesMap.put(COLUMN_NAME, item.getName());
		return valuesMap;
	}

	@Override
	protected Map<String, String> getSearchColumnsMap() {
		throw new UnsupportedOperationException();

	}

	@Override
	public SearchData cursorToSearchObject(Cursor cursor) {
		throw new UnsupportedOperationException();

	}
	
	public List<CategoryData> getAll() {
		List<CategoryData> data = new ArrayList<CategoryData>();
		Cursor cursor = mDataBase.rawQuery(String.format("select * from %", TABLE_NAME), null);
		try {
			if (cursor.moveToFirst()) {
				while(!cursor.isAfterLast()) {
					data.add(cursorToObjectInternal(cursor));
					cursor.moveToNext();
				}
			}
		} finally {
			cursor.close();
		}
		return data;
	}
	
}
