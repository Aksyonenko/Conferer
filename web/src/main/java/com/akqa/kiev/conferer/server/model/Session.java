
package com.akqa.kiev.conferer.server.model;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sessions")
public class Session extends AbstractEntity {

    public enum Type {
        Tutorial, Workshop, OpenDiscussion, Presentation, Lunch
    }

    private String sessionUrl;
    private String title;
    private Type type;
    private String summary;
    private Date startTime;
    private Date endTime;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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


}
