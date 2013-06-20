package com.akqa.kiev.android.conferer.test.web.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.akqa.kiev.android.conferer.utils.IoUtils;
import com.akqa.kiev.android.conferer.web.json.ConferencesMonthsListJsonParser;
import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

public class ConferencesMonthsListJsonParserTest extends TestCase {

	private ConferencesMonthsListJsonParser conferencesMonthsListJsonParser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		conferencesMonthsListJsonParser = new ConferencesMonthsListJsonParser();
	}

	public void testConferencesMonthsListJsonParserParseCorrectlyThreeItems()
			throws IOException, JsonParseException {
		InputStream is = ConferencesJsonParserTest.class
				.getResourceAsStream("/com/akqa/kiev/android/conferer/test/web/json/conferencesMonthsList.json");
		String jsonString = IoUtils.getStringDataFromStream(is);
		List<Long> conferencesMonths = conferencesMonthsListJsonParser
				.parseConferencesMonths(jsonString);
		Assert.assertNotNull(conferencesMonths);
		Assert.assertEquals(3, conferencesMonths.size());

		Assert.assertEquals(1362924050800L, conferencesMonths.get(0)
				.longValue());
		Assert.assertEquals(1365598850800L, conferencesMonths.get(1)
				.longValue());
		Assert.assertEquals(1368190850800L, conferencesMonths.get(2)
				.longValue());
	}

	public void testParseExceptionThrownOnInvalidInput() {
		try {
			conferencesMonthsListJsonParser
					.parseConferencesMonths("invalid JSON!!!");
			fail("Expected Exception JsonParseException");
		} catch (JsonParseException e) {
			// do nothing, it's OK
		}
	}
}
