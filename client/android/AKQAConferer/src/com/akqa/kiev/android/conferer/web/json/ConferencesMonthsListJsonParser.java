package com.akqa.kiev.android.conferer.web.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

/**
 * Json parser for list of months that have at least 1 conference.
 * 
 * @author Yuriy.Belelya
 * 
 */
public class ConferencesMonthsListJsonParser extends AbstractJsonParser<Long> {

	public List<Long> parseConferencesMonths(String jsonString)
			throws JsonParseException {
		if (jsonString == null) {
			return null;
		}
		List<Long> months = null;
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			months = parseJsonListObjects(jsonArray);
		} catch (JSONException e) {
			throw new JsonParseException(e);
		}
		return months;
	}
}
