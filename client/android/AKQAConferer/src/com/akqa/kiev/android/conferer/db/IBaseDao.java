package com.akqa.kiev.android.conferer.db;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface IBaseDao<T> {

	void init(SQLiteDatabase db);

	T getById(Long id);

	List<T> findAll();

	void insert(T item);

	void insertList(List<T> items);

	Cursor searchQuery(String searchArg);

}
