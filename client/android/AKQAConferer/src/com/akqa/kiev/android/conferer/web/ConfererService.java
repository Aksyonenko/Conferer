package com.akqa.kiev.android.conferer.web;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.util.Log;

import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.utils.LogUtils;
import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;
import com.akqa.kiev.android.conferer.web.json.ReflectionJsonParsingHelper;

public class ConfererService {

	private ConfererWebClient webClient;

	public ConfererService() {
		webClient = new ConfererWebClient();
	}

	public List<Long> loadConferencesMonths() {
		String allConfMonthsJson = webClient.getAllconferencesMonths();
		try {
			return ReflectionJsonParsingHelper.listObjectsFromJsonString(
					allConfMonthsJson, Long.class);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
			e.printStackTrace();
		}
		return null;
	}

	public List<ConferenceData> loadConferencesForMonth(long date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTimeInMillis(date);
		String conferencesJson = webClient.getConferences(
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
		try {
			return ReflectionJsonParsingHelper.listObjectsFromJsonString(
					conferencesJson, ConferenceData.class);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
		}
		return null;
	}

	public ConferenceDetailsData loadConferenceDetails(long id) {
		String conferenceDetailsJson = webClient.getConferenceDetails(id);
		try {
			return ReflectionJsonParsingHelper.objectFromJsonString(
					conferenceDetailsJson, ConferenceDetailsData.class);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
		}
		return null;
	}

	public SessionData loadSessionDetails(long id) {
		String conferenceDetailsJson = webClient.getSessionDetails(id);
		try {
			return ReflectionJsonParsingHelper.objectFromJsonString(
					conferenceDetailsJson, SessionData.class);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
		}
		return null;
	}

	public SpeakerData loadSpeakereDetails(long id) {
		String conferenceDetailsJson = webClient.getSpeakerDetails(id);
		try {
			return ReflectionJsonParsingHelper.objectFromJsonString(
					conferenceDetailsJson, SpeakerData.class);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
		}
		return null;
	}
}
