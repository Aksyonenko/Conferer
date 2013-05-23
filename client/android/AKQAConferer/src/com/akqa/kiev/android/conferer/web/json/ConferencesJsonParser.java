package com.akqa.kiev.android.conferer.web.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

/**
 * Json parser for list of conferences.
 * 
 * @author Yuriy.Belelya
 * 
 */
public class ConferencesJsonParser extends AbstractJsonParser<ConferenceData> {

	public List<ConferenceData> parseConferences(String jsonString)
			throws JsonParseException {
		List<ConferenceData> conferences = null;
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			conferences = parseJsonListObjects(jsonArray);
		} catch (JSONException e) {
			throw new JsonParseException(e);
		}
		return conferences;
	}
}
