package com.akqa.kiev.conferer.server.model;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.akqa.kiev.conferer.server.dao.json.FullView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "conferences")
@NamedQueries({
	@NamedQuery(
		name = "Conference.findByMonthAndYear",
		query = "SELECT c FROM Conference AS c WHERE happens_in_month_year(?1, ?2, c.startDate, c.endDate, c.timezone) = TRUE"
	),
	
	@NamedQuery(
			name = "Conference.findActiveMonths",
			query = "SELECT c FROM Conference c"
	)	
})
public class Conference extends AbstractEntity {

	@Column(length = 1024)
	private String conferenceUrl;
	
	@Column(length = 1024)
	private String logoUrl;
	
	@Column(length = 255, nullable = false)
	private String title;
	
	@Column(length = 4096)
	private String summary;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar startDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar endDate;
	
	@JsonIgnore
	@Column(length = 32, nullable = false)
	private String timezone;

	@Column(length = 64, nullable = false)
	private String country;
	
	@Column(length = 64, nullable = false)
	private String region;
	
	@Column(length = 64, nullable = false)
	private String city;
	
	@Column(length = 128, nullable = false)
	private String address;

	@JsonView(FullView.class)
	@Column(length = 4096)
	private String details;

	@JsonView(FullView.class)
	@OneToMany(targetEntity = Session.class, fetch = FetchType.LAZY)
	@JoinTable(
		name = "conferences_sessions",
		joinColumns = @JoinColumn(name = "conference_id", referencedColumnName = "id", nullable = false, unique = false),
		inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false, unique = true)
	)
	private List<Session> sessions;

    @PostLoad
    public void postLoad() {
        startDate.setTimeZone(TimeZone.getTimeZone(timezone));
        endDate.setTimeZone(TimeZone.getTimeZone(timezone));
    }

	public String getConferenceUrl() {
		return conferenceUrl;
	}

	public void setConferenceUrl(String conferenceUrl) {
		this.conferenceUrl = conferenceUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
}
