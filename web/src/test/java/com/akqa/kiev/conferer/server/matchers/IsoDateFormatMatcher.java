package com.akqa.kiev.conferer.server.matchers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class IsoDateFormatMatcher extends BaseMatcher<String> {

	private static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(ISO_FORMAT);
	
	@Override
	public boolean matches(Object item) {
		if (item instanceof String == false) return false;
		
		try {
			DATE_FORMATTER.parse((String) item);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("a String format according to ISO8601 \"" + ISO_FORMAT + "\"");
	}

}
