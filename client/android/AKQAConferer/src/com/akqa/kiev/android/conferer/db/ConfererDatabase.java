package com.akqa.kiev.android.conferer.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.utils.LogUtils;
import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;
import com.akqa.kiev.android.conferer.web.json.ReflectionJsonParsingHelper;

public class ConfererDatabase {
	
	private static final String DATABASE_NAME = "conferer.db";
	private static final int DATABASE_VERSION = 1;

	protected SQLiteDatabase mDataBase;

	private ConfererWebClient client;

	private ConferenceDao conferenceDao;
	private SessionDao sessionDao;
	private SpeakerDao speakerDao;
	
	private CountDownLatch countDownLatch;

	public ConfererDatabase(Context context) {
		client = new ConfererWebClient();
		conferenceDao = new ConferenceDao();
		sessionDao = new SessionDao();
		speakerDao = new SpeakerDao();

		mDataBase = new OpenHelper(context).getWritableDatabase();
		conferenceDao.setDataBase(mDataBase);
		sessionDao.setDataBase(mDataBase);
		speakerDao.setDataBase(mDataBase);
		waitForDbInitIfFirstCall();
	}

	public ConferenceDao getConferenceDao() {
		return conferenceDao;
	}

	public SessionDao getSessionDao() {
		return sessionDao;
	}

	public SpeakerDao getSpeakerDao() {
		return speakerDao;
	}
	
	private void init(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			conferenceDao.init(db);
			speakerDao.init(db);
			sessionDao.init(db);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	private void initTablesData(List<ConferenceDetailsData> data) {
		mDataBase.beginTransaction();
		try {
			List<Long> insertedSpeakers = new ArrayList<Long>();
			for (ConferenceDetailsData conferenceDetailsData : data) {
				conferenceDao.insert(conferenceDetailsData);
				List<SessionData> sessions = conferenceDetailsData
						.getSessions();
				Long conferenceId = conferenceDetailsData.getId();
				for (SessionData session : sessions) {
					sessionDao.insert(session, conferenceId);
					List<SpeakerData> speakers = session.getSpeakers();
					for (SpeakerData speaker : speakers) {
						if (!insertedSpeakers.contains(speaker.getId())) {
							speakerDao.insert(speaker);
							insertedSpeakers.add(speaker.getId());
						}
						sessionDao.assignSpeakerToSession(session.getId(),
								speaker.getId());
					}
				}
			}
			mDataBase.setTransactionSuccessful();
		} finally {
			mDataBase.endTransaction();
		}
	}
	
	private class OpenHelper extends SQLiteOpenHelper {

		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			init(db);
			countDownLatch = new CountDownLatch(1);
			LoadDataTask loadDataTask = new LoadDataTask();
			loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
		}
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// do nothing
		}
	}
	
	private void waitForDbInitIfFirstCall() {
		if (countDownLatch != null) {
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				LogUtils.logE(getClass().getName(), e);
			}
		}
	}
	
	private class LoadDataTask extends
			AsyncTask<Void, Void, List<ConferenceDetailsData>> {
		@Override
		protected List<ConferenceDetailsData> doInBackground(Void... params) {
			String allConferencesDetailsJson = client.getAllConferences();
			try {
				List<ConferenceDetailsData> result = ReflectionJsonParsingHelper
						.listObjectsFromJsonString(allConferencesDetailsJson,
								ConferenceDetailsData.class);
				if (result != null) {
					initTablesData(result);
				}
				return result;
			} catch (Exception e) {
				LogUtils.logE(getClass().getName(), e);
			} finally {
				countDownLatch.countDown();

			}
			return null;
		}

	}
}
