package com.akqa.kiev.android.conferer.test.web.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;

import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SocialLinksData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.utils.IoUtils;
import com.akqa.kiev.android.conferer.web.json.ReflectionJsonParsingHelper;

public class ReflectionJsonParsingHelperTest extends TestCase {

	public void testConferencesMonthsListJsonParsing() throws IOException,
			InstantiationException, IllegalAccessException, JSONException,
			InvocationTargetException, NoSuchMethodException, ParseException {
		InputStream is = getClass()
				.getResourceAsStream(
						"/com/akqa/kiev/android/conferer/test/web/json/conferences_months.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		List<Long> conferencesMonths = ReflectionJsonParsingHelper
				.listObjectsFromJsonString(jsonString, Long.class);

		assertNotNull(conferencesMonths);
		assertEquals(3, conferencesMonths.size());
		assertEquals(1356998400000L, conferencesMonths.get(0).longValue());
		assertEquals(1362096000000L, conferencesMonths.get(1).longValue());
		assertEquals(1364774400000L, conferencesMonths.get(2).longValue());
	}

	public void testConferencesJsonParsing() throws IOException,
			InstantiationException, IllegalAccessException, JSONException,
			InvocationTargetException, NoSuchMethodException, ParseException {
		InputStream is = getClass()
				.getResourceAsStream(
						"/com/akqa/kiev/android/conferer/test/web/json/conferences.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		List<ConferenceData> conferences = ReflectionJsonParsingHelper
				.listObjectsFromJsonString(jsonString, ConferenceData.class);
		assertNotNull(conferences);
		assertEquals(3, conferences.size());

		ConferenceData conference = conferences.get(0);
		assertEquals(Long.valueOf(1), conference.getId());

		assertEquals("QCon New York 2013", conference.getTitle());
		assertEquals(
				"https://qconnewyork.com/sites/all/themes/qcon_master/images/logo.jpg",
				conference.getLogoUrl());

		assertEquals(1357102800000L, conference.getStartDate().getTime());
		assertEquals(1357362000000L, conference.getEndDate().getTime());

		assertEquals(
				"QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams.",
				conference.getSummary());
		assertEquals("Japan", conference.getCountry());
		assertEquals("Tokyo", conference.getCity());
		assertEquals("Kanto", conference.getRegion());
	}

	public void testConferenceDetailsJsonParsing() throws IOException,
			InstantiationException, IllegalAccessException, JSONException,
			InvocationTargetException, NoSuchMethodException, ParseException {
		InputStream is = getClass()
				.getResourceAsStream(
						"/com/akqa/kiev/android/conferer/test/web/json/conference_details.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		ConferenceDetailsData conferenceDetails = ReflectionJsonParsingHelper
				.objectFromJsonString(jsonString, ConferenceDetailsData.class);

		assertNotNull(conferenceDetails);
		assertEquals(Long.valueOf(1), conferenceDetails.getId());

		assertEquals("QCon New York 2013", conferenceDetails.getTitle());
		assertEquals("dummy conference url",
				conferenceDetails.getConferenceUrl());
		assertEquals(
				"https://qconnewyork.com/sites/all/themes/qcon_master/images/logo.jpg",
				conferenceDetails.getLogoUrl());

		assertEquals(1357102800000L, conferenceDetails.getStartDate().getTime());
		assertEquals(1357362000000L, conferenceDetails.getEndDate().getTime());

		assertEquals(
				"QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams.",
				conferenceDetails.getSummary());
		assertEquals("Japan", conferenceDetails.getCountry());
		assertEquals("Tokyo", conferenceDetails.getCity());
		assertEquals("Kanto", conferenceDetails.getRegion());
		assertEquals("dummy address", conferenceDetails.getAddress());

		List<SessionData> sessions = conferenceDetails.getSessions();
		assertNotNull(sessions);
		assertEquals(4, sessions.size());

		SessionData session = sessions.get(0);
		assertNotNull(session);
		assertEquals(Long.valueOf(1), session.getId());
		assertNull(session.getSessionUrl());
		assertEquals("http://shinjukuus.com/assets/images/shinjuku-logo-3.png",
				session.getSessionLogoUrl());
		assertEquals("Avoiding Invisible Impediments to High Performance",
				session.getTitle());
		assertEquals("Workshop", session.getType());
		assertEquals(
				"This tutorial assumes the following hypothesis: Learning is the Bottleneck of Software Development and Delivery, and asks the question \"what is keeping us from learning effectively?\" There are some things that are visible such as the length of the release cycle, the clarity of the goal, learning from mistakes and difficulties, etc.... This is valuable, but not what this tutorial is about.",
				session.getSummary());
		assertNull(session.getDetails());
		assertNotNull(session.getStartTime());
		assertNotNull(session.getEndTime());

		List<SpeakerData> speakers = session.getSpeakers();
		assertNotNull(speakers);
		assertEquals(1, speakers.size());

		SpeakerData speaker = speakers.get(0);
		assertNotNull(speaker);
		assertEquals(Long.valueOf(1), speaker.getId());
		assertEquals("some url", speaker.getSpeakerUrl());
		assertEquals("Adam", speaker.getFirstName());
		assertEquals("Blum", speaker.getLastName());
		assertEquals(
				"http://www.bicompetenceforum.com/wp-content/uploads/2013/04/ruudhasselerharm.jpg",
				speaker.getPhotoUrl());
		assertEquals(
				"Toby O'Rourke is a Technical Architect at Gamesys, a London based soft-gaming company. With a background as a Java guy, he considers himself lucky to rove around the tech landscape at Gamesys combining architecture and hands on development with exposure to a fairly eclectic mix of client, server and native mobile technologies. He believes an excited team, plenty of domain modelling and a beady eye on the details are decent starting point for delivering just about anything.",
				speaker.getAbout());
		assertNotNull(speaker.getSocialLinks());
		SocialLinksData socialLinks = speaker.getSocialLinks();
		assertEquals("facebook link", socialLinks.getFacebook());
		assertEquals("twitter link", socialLinks.getTwitter());
		assertEquals("linkedin link", socialLinks.getLinkedin());
	}

	public void testSessionDetailsJsonParsing() throws IOException,
			InstantiationException, IllegalAccessException, JSONException,
			InvocationTargetException, NoSuchMethodException, ParseException {
		InputStream is = getClass()
				.getResourceAsStream(
						"/com/akqa/kiev/android/conferer/test/web/json/session_details.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		SessionData session = ReflectionJsonParsingHelper.objectFromJsonString(
				jsonString, SessionData.class);

		assertNotNull(session);
		assertEquals(Long.valueOf(1), session.getId());
		assertNull(session.getSessionUrl());
		assertEquals("http://shinjukuus.com/assets/images/shinjuku-logo-3.png",
				session.getSessionLogoUrl());
		assertEquals("Avoiding Invisible Impediments to High Performance",
				session.getTitle());
		assertEquals("Workshop", session.getType());
		assertEquals(
				"This tutorial assumes the following hypothesis: Learning is the Bottleneck of Software Development and Delivery, and asks the question \"what is keeping us from learning effectively?\" There are some things that are visible such as the length of the release cycle, the clarity of the goal, learning from mistakes and difficulties, etc.... This is valuable, but not what this tutorial is about.",
				session.getSummary());
		assertNull(session.getDetails());
		assertNotNull(session.getStartTime());
		assertNotNull(session.getEndTime());

		List<SpeakerData> speakers = session.getSpeakers();
		assertNotNull(speakers);
		assertEquals(1, speakers.size());

		SpeakerData speaker = speakers.get(0);
		assertNotNull(speaker);
		assertEquals(Long.valueOf(1), speaker.getId());
		assertEquals("some url", speaker.getSpeakerUrl());
		assertEquals("Adam", speaker.getFirstName());
		assertEquals("Blum", speaker.getLastName());
		assertEquals(
				"http://www.bicompetenceforum.com/wp-content/uploads/2013/04/ruudhasselerharm.jpg",
				speaker.getPhotoUrl());
		assertEquals(
				"Toby O'Rourke is a Technical Architect at Gamesys, a London based soft-gaming company. With a background as a Java guy, he considers himself lucky to rove around the tech landscape at Gamesys combining architecture and hands on development with exposure to a fairly eclectic mix of client, server and native mobile technologies. He believes an excited team, plenty of domain modelling and a beady eye on the details are decent starting point for delivering just about anything.",
				speaker.getAbout());
		assertNotNull(speaker.getSocialLinks());
		SocialLinksData socialLinks = speaker.getSocialLinks();
		assertEquals("facebook link", socialLinks.getFacebook());
		assertEquals("twitter link", socialLinks.getTwitter());
		assertEquals("linkedin link", socialLinks.getLinkedin());
	}

	public void testSpeakerDetailsJsonParsing() throws IOException,
			InstantiationException, IllegalAccessException, JSONException,
			InvocationTargetException, NoSuchMethodException, ParseException {
		InputStream is = getClass()
				.getResourceAsStream(
						"/com/akqa/kiev/android/conferer/test/web/json/speaker_details.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		SpeakerData speaker = ReflectionJsonParsingHelper.objectFromJsonString(
				jsonString, SpeakerData.class);

		assertNotNull(speaker);
		assertEquals(Long.valueOf(1), speaker.getId());
		assertEquals("some url", speaker.getSpeakerUrl());
		assertEquals("Adam", speaker.getFirstName());
		assertEquals("Blum", speaker.getLastName());
		assertEquals(
				"http://www.bicompetenceforum.com/wp-content/uploads/2013/04/ruudhasselerharm.jpg",
				speaker.getPhotoUrl());
		assertEquals(
				"Toby O'Rourke is a Technical Architect at Gamesys, a London based soft-gaming company. With a background as a Java guy, he considers himself lucky to rove around the tech landscape at Gamesys combining architecture and hands on development with exposure to a fairly eclectic mix of client, server and native mobile technologies. He believes an excited team, plenty of domain modelling and a beady eye on the details are decent starting point for delivering just about anything.",
				speaker.getAbout());
		assertNotNull(speaker.getSocialLinks());
		SocialLinksData socialLinks = speaker.getSocialLinks();
		assertEquals("facebook link", socialLinks.getFacebook());
		assertEquals("twitter link", socialLinks.getTwitter());
		assertEquals("linkedin link", socialLinks.getLinkedin());
		assertEquals("Game Developer", speaker.getCompetence());
	}

}
