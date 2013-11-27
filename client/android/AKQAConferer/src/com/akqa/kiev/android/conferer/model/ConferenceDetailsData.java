package com.akqa.kiev.android.conferer.model;

import java.util.List;

public class ConferenceDetailsData extends ConferenceData {

	private List<SessionData> sessions;

	public List<SessionData> getSessions() {
		return sessions;
	}

	public void setSessions(List<SessionData> sessions) {
		this.sessions = sessions;
	}
	
	public ConferenceDetailsData() {

	}

	public ConferenceDetailsData(ConferenceData data) {
		setId(data.getId());
		setConferenceUrl(data.getConferenceUrl());
		setLogoUrl(data.getLogoUrl());
		setTitle(data.getTitle());
		setSummary(data.getSummary());
		setStartDate(data.getStartDate());
		setEndDate(data.getEndDate());
		setCountry(data.getCountry());
		setRegion(data.getRegion());
		setCity(data.getCity());
		setAddress(data.getAddress());
	}
}
