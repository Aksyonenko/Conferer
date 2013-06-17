package com.akqa.kiev.conferer.server.model;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sessions")
public class Session extends AbstractEntity {
	
	@Column(length = 1024, nullable = false)
	private String sessionUrl;
	
	@Column(length = 512, nullable = false)
	private String title;
	
	@Column(length = 32)
	private String type;
	
	@Column(length = 4096)
	private String summary;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar startTime;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endTime;
	
	@JsonIgnore
	@Column(length = 32, nullable = false)
	private String timezone;
	
	@Column(length = 16384)
	private String details;

	@ManyToMany
	@JoinTable(
		name = "sessions_speakers",
		joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false, unique = false),
		inverseJoinColumns = @JoinColumn(name = "speaker_id", referencedColumnName = "id", nullable = false, unique = false)
	)
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
