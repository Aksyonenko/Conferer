package com.akqa.kiev.android.conferer.test.web.client;

import junit.framework.TestCase;

import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;

public class ConfererWebClientTest extends TestCase {

	private ConfererWebClient client;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		client = new ConfererWebClient();
	}

	public void testGetConferences() {
		String conferences = client.getConferences(2013, 1);
		assertNotNull(conferences);
	}

	public void testGetAllConferencesMonths() {
		String confMonths = client.getAllconferencesMonths();
		assertNotNull(confMonths);
	}

	public void testGetAllConferences() {
		String allConferences = client.getAllConferences();
		assertNotNull(allConferences);
	}

	public void testGetConferenceDetails() {
		String confDetails = client.getConferenceDetails(1);
		assertNotNull(confDetails);
	}

	public void testGetSessioneDetails() {
		String sessionDetails = client.getSessionDetails(1);
		assertNotNull(sessionDetails);
	}

	public void testGetSpeakerDetails() {
		String speakerDetails = client.getSpeakerDetails(1);
		assertNotNull(speakerDetails);
	}
	
	public void testGetSpeakerSessions() {
		String speakerSessions = client.getSpeakerSessions(1);
		assertNotNull(speakerSessions);
	}

}
