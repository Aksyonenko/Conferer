package com.akqa.kiev.conferer.server.model;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.PostLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Session extends AbstractEntity {

	private String sessionUrl;
	private String title;
	private String type;
	private String summary;
	
	private Calendar startTime;
	private Calendar endTime;
	
	@JsonIgnore
	private String timezone;
	
	private String details;

	private List<Speaker> speakers;

	@PostLoad
	public void postLoad() {
		startTime.setTimeZone(TimeZone.getTimeZone(timezone));
		endTime.setTimeZone(TimeZone.getTimeZone(timezone));
	}
	
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

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
