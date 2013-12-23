package com.akqa.kiev.android.conferer.service;

import java.util.List;

import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;

public interface ConfererService {

	List<Long> loadConferencesMonths();

	List<ConferenceData> loadConferencesForMonth(long date);

	ConferenceDetailsData loadConferenceDetails(long id);

	SessionData loadSessionDetails(long id);

	SpeakerData loadSpeakerDetails(long id);
	
	List<SessionData> loadSpeakerSessions(long speakerId);
}
