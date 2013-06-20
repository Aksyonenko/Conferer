package com.akqa.kiev.android.conferer.test.web.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.utils.IoUtils;
import com.akqa.kiev.android.conferer.web.json.ConferencesJsonParser;
import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

public class ConferencesJsonParserTest extends TestCase {

	private ConferencesJsonParser conferencesJsonParser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		conferencesJsonParser = new ConferencesJsonParser();
	}

	public void testConferencesJsonParserParseCorrectlyThreeItems()
			throws IOException, JsonParseException {
		InputStream is = ConferencesJsonParserTest.class
				.getResourceAsStream("/com/akqa/kiev/android/conferer/test/web/json/conferences.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		List<ConferenceData> conferences = conferencesJsonParser
				.parseConferences(jsonString);
		Assert.assertNotNull(conferences);
		Assert.assertEquals(3, conferences.size());

		ConferenceData conference = conferences.get(0);

		Assert.assertEquals("QCon New York 2013", conference.getTitle());
		Assert.assertEquals("dummy logo url", conference.getLogoUrl());

		Assert.assertEquals(1357084800000L, conference.getStartDate().getTime());
		Assert.assertEquals(1357344000000L, conference.getEndDate().getTime());

		Assert.assertEquals(
				"QCon empowers software development by facilitating the spread of knowledge and innovation in the developer community. A practitioner-driven conference, QCon is designed for technical team leads, architects, engineering directors, and project managers who influence innovation in their teams.",
				conference.getSummary());
		Assert.assertEquals("Japan", conference.getCountry());
		Assert.assertEquals("Tokyo", conference.getCity());
		Assert.assertEquals("Kanto", conference.getRegion());

	}

	public void testParseExceptionThrownOnInvalidInput() {
		try {
			conferencesJsonParser.parseConferences("invalid JSON!!!");
			fail("Expected Exception JsonParseException");
		} catch (JsonParseException e) {
			// do nothing, it's OK
		}

	}
}
