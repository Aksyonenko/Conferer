package com.akqa.kiev.conferer.server.dao;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import net.minidev.json.JSONObject;

import com.akqa.kiev.conferer.server.matchers.IsoDateFormatMatcher;
import com.jayway.jsonpath.JsonPath;

public class ImportLauncher {

	private static final String JS_FILE = "mongo/integration-testing.js";

	public static void main(String[] args) throws Exception {
		generateSpeakersSql();
	}

	private static void generateSpeakersSql() throws IOException {
//		Reader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream(JS_FILE), Charset.forName("UTF-8"));
		List<JSONObject> sessions = JsonPath.<List<JSONObject>>read(ClassLoader.getSystemResource(JS_FILE), "$.speakers[*]");
		
		String[] fields = {"_id", "speakerUrl", "firstName", "lastName", "photoUrl", "about", "socialLinks.facebook", "socialLinks.twitter", "socialLinks.linkedin"};
		StringBuilder builder = new StringBuilder();
		
		for (JSONObject session : sessions) {
			builder.append("(\r\n");
			
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				Object value;
				
				if (field.startsWith("socialLinks")) {
					field = field.substring("socialLinks.".length());
					JSONObject jsonObject = (JSONObject) session.get("socialLinks");
					value = jsonObject.get(field);
				} else {
					value = session.get(field);
				}
				
				builder.append("\t");
				
				if (field.equals("_id")) {
					builder.append(value);
				} else {
					String str = (String) value;
					str = str.replace("'", "''");
					builder.append("'").append(str).append("'");
				}
				
				builder.append(",\r\n");
			}
			
			builder.append("), ");
		}
		
		System.out.println(builder.toString());
	}
	
	private static void generateSessionsSql() throws IOException {
		InputStream resource = ClassLoader.getSystemResourceAsStream(JS_FILE);
		List<JSONObject> sessions = JsonPath.<List<JSONObject>>read(resource, "$.conferences[*].sessions[*]");
		
		String[] fields = {"_id", "title", "summary", "startTime", "endTime", "type"};
		StringBuilder builder = new StringBuilder();
		
		DateFormat dateFormatter = new SimpleDateFormat(IsoDateFormatMatcher.ISO_FORMAT);
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		for (JSONObject session : sessions) {
			builder.append("(\r\n");
			
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				Object value = session.get(field);
				builder.append("\t");
				
				if (field.equals("_id")) {
					builder.append(value);
				} else if (field.endsWith("Time")) {
					JSONObject jsonObj = (JSONObject) value;
					Date date = new Date((Long) jsonObj.get("$date"));
					String formattedDate = String.format("PARSEDATETIME('%s', 'dd-MM-yyyy''T''hh:mm:ss.SSSZ')", dateFormatter.format(date));
					builder.append(formattedDate);
				} else {
					builder.append("'").append(value).append("'");
				}
				
				if (i < (fields.length - 1)) builder.append(",");
				builder.append("\r\n");
			}
			
			builder.append("), ");
		}
		
		System.out.println(builder.toString());
	}

}
