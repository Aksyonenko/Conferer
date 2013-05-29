package com.akqa.kiev.conferer.server.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "sessions")
public class Session extends AbstractEntity {

	private String sessionUrl;
	private String title;
	private String type;
	private String summary;
	final private Calendar startTime = Calendar.getInstance();
	final private Calendar endTime = Calendar.getInstance();
	private String details;

	@DBRef
	private ArrayList<Speaker> speakers;

	public String getSessionUrl() {
		return sessionUrl;
	}

	public void setSessionUrl(String sessionUrl) {
		this.sessionUrl = sessionUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getStartTime() {
		return startTime.getTime();
	}

	public void setStartTime(Date startTime) {
		this.startTime.setTime(startTime);
	}

	public Date getEndTime() {
		return endTime.getTime();
	}

	public void setEndTime(Date endTime) {
		this.endTime.setTime(endTime);
	}

	public ArrayList<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(ArrayList<Speaker> speakers) {
		this.speakers = speakers;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	/**
	 * @return the timezone
	 */
	@JsonIgnore
	public String getTimezone() {
		return startTime.getTimeZone().getDisplayName();
	}
	
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		TimeZone zone = TimeZone.getTimeZone(timezone);
		startTime.setTimeZone(zone);
		endTime.setTimeZone(zone);
	}

}
