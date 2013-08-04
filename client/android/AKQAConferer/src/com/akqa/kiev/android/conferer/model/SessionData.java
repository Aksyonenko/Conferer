package com.akqa.kiev.android.conferer.model;

import java.util.Date;
import java.util.List;

public class SessionData implements Comparable<SessionData> {

	private Long id;
	private String sessionUrl;
	private String sessionLogoUrl;
	private String title;
	private String type;
	private String summary;
	private Date startTime;
	private Date endTime;
	private String details;
	private String location;
	private List<SpeakerData> speakers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionUrl() {
		return sessionUrl;
	}

	public void setSessionUrl(String sessionUrl) {
		this.sessionUrl = sessionUrl;
	}

	public String getSessionLogoUrl() {
		return sessionLogoUrl;
	}

	public void setSessionLogoUrl(String sessionLogoUrl) {
		this.sessionLogoUrl = sessionLogoUrl;
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
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<SpeakerData> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<SpeakerData> speakers) {
		this.speakers = speakers;
	}

	@Override
	public int compareTo(SessionData another) {
		if (another == null) {
			return 1;
		}
		if (startTime == null && another.getStartTime() == null) {
			return 0;
		}
		if (startTime == null) {
			return -1;
		} else if (another.getStartTime() == null) {
			return 1;
		}
		return startTime.compareTo(another.getStartTime());
	}

}
