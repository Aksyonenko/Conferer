package com.akqa.kiev.conferer;

import java.util.ArrayList;
import java.util.Date;

public class Conference {
	private String conferenceId;
	private String conferenceUrl;
	private String logoUrl;
	private String title;
	private String summary;
	private Date startDate;
	private Date endDate;
	private String country;
	private String region;
	private String city;
	private String address;
	private String details;
	
	private ArrayList<Session> sessions;
	
	public String getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
    public ArrayList<Session> getSessions() {
		return sessions;
	}
	public void setSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}
}
