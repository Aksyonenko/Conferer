package com.akqa.kiev.conferer.server.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.data.mongodb.core.mapping.Document;

import com.akqa.kiev.conferer.server.dao.json.IsoDateSerializer;
import com.akqa.kiev.conferer.server.dao.json.FullView;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document(collection = "conferences")
@JsonAutoDetect(getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class Conference extends AbstractEntity {
	
	private String conferenceUrl;
	private String logoUrl;
	private String title;
	private String summary;
	
	@JsonSerialize(using = IsoDateSerializer.class)
	private Calendar startDate = Calendar.getInstance();
	
	@JsonSerialize(using = IsoDateSerializer.class)
	
	private Calendar endDate = Calendar.getInstance();
	
	private String country;
	private String region;
	private String city;
	private String address;
	
	@JsonView(FullView.class)
	private String details;
	
	@JsonView(FullView.class)
	private List<Session> sessions;

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
	
	public Date getStartDate() {
		return startDate.getTime();
	}
	public void setStartDate(Date startDate) {
		this.startDate.setTime(startDate);
	}
	public Date getEndDate() {
		return endDate.getTime();
	}
	public void setEndDateTime(Date endDate) {
		this.endDate.setTime(endDate);
	}
	public String getTimezone() {
		return startDate.getTimeZone().getDisplayName();
	}
	public void setTimezone(String timezone) {
		TimeZone zone = TimeZone.getTimeZone(timezone);
		startDate.setTimeZone(zone);
		endDate.setTimeZone(zone);
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
