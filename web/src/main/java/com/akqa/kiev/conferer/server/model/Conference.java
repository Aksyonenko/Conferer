package com.akqa.kiev.conferer.server.model;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.PostLoad;

import com.akqa.kiev.conferer.server.dao.json.FullView;
import com.fasterxml.jackson.annotation.JsonView;

public class Conference extends AbstractEntity {

	private String conferenceUrl;
	private String logoUrl;
	private String title;
	private String summary;

	private Calendar startDate;
	private Calendar endDate;
	private String timezone;

	private String country;
	private String region;
	private String city;
	private String address;

	@JsonView(FullView.class)
	private String details;

	@JsonView(FullView.class)
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
