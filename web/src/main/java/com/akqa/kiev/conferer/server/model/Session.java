package com.akqa.kiev.conferer.server.model;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

    @Column(length = 1024)
    private String sessionLogoUrl;

    @Column(length = 64)
    private String location;

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
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="conference_id")
	private Conference conference;

	public Conference getConference() {
		return conference;
	}

	public String getDetails() {
		return details;
	}

	public Calendar getEndTime() {
		return endTime;
	}
	
	public String getLocation() {
        return location;
    }

	public String getSessionLogoUrl() {
        return sessionLogoUrl;
    }

    public String getSessionUrl() {
		return sessionUrl;
	}

    public List<Speaker> getSpeakers() {
		return speakers;
	}

    public Calendar getStartTime() {
		return startTime;
	}

    public String getSummary() {
		return summary;
	}

	public String getTimezone() {
		return timezone;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	@PostLoad
	public void postLoad() {
		startTime.setTimeZone(TimeZone.getTimeZone(timezone));
		endTime.setTimeZone(TimeZone.getTimeZone(timezone));
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public void setLocation(String location) {
        this.location = location;
    }

	public void setSessionLogoUrl(String sessionLogoUrl) {
        this.sessionLogoUrl = sessionLogoUrl;
    }

	public void setSessionUrl(String sessionUrl) {
		this.sessionUrl = sessionUrl;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}
}
