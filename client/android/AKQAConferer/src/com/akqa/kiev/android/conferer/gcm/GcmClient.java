package com.akqa.kiev.android.conferer.gcm;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmClient {
	
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_REG_ID = "registration_id";
	private GoogleCloudMessaging gcm;
	private Activity activity;
	private String registrationId;
	private ConfererWebClient webClient = new ConfererWebClient();

	public boolean checkPlayServices(Activity activity) {
		this.activity = activity;
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(activity);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(getClass().getName(), "This device is not supported.");
				activity.finish();
			}
			return false;
		} else {
			registrationId = getRegistrationId();
			if (registrationId == null) {
				registerInBackground(activity.getResources().getString(R.string.gcm_sender_id));
			}
		}
		return true;
	}
	
	private String getRegistrationId() {
		final SharedPreferences prefs = getGcmPreferences(activity);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			return null;
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion();
		if (registeredVersion != currentVersion) {
			Log.i(getClass().getName(), "App version changed.");
			return null;
		}
		return registrationId;
	}

	private void registerInBackground(final String senderId) {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(activity);
					}
					registrationId = gcm.register(senderId);
					msg = "Device registered, registration ID="
							+ registrationId;
					boolean registeredInBackend = sendRegistrationIdToBackend();
					if (registeredInBackend) {
						storeRegistrationId(registrationId);

					}				
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

		}.execute(null, null, null);

	}

	private boolean sendRegistrationIdToBackend() {
		String answer = webClient.registerDevice(registrationId);
		return answer != null && answer.contains(registrationId);
	}

	private void storeRegistrationId(String regId) {
		final SharedPreferences prefs = getGcmPreferences(activity);
		int appVersion = getAppVersion();
		Log.i(getClass().getName(), "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private SharedPreferences getGcmPreferences(Activity activity) {
		return activity.getSharedPreferences(activity.getClass()
				.getSimpleName(), Context.MODE_PRIVATE);
	}

	private int getAppVersion() {
		try {
			PackageInfo packageInfo = activity.getPackageManager()
					.getPackageInfo(activity.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

}
