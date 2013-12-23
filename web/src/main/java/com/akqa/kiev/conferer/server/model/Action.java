
package com.akqa.kiev.conferer.server.model;


import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "actions")
public class Action extends AbstractEntity {

    @Column(name = "action_name", nullable = false)
    private String actionName;

    @Column(name = "entity_class", nullable = false)
    private String entityClass;

    @Column(name = "entity_id", nullable = false)
    private BigInteger entityId;

    @JsonIgnore
    @Column(name = "db_timestamp", nullable = false)
    private Date dbTimestamp;

    @JsonIgnore
    @Column(name = "sent_to_device", nullable = false)
    private Boolean sentToDevice;

    public Action() {
        dbTimestamp = new Date();
        sentToDevice = false;
    }

    public Action(String actionName, String entityClass, BigInteger entityId) {
        this();
        this.actionName = actionName;
        this.entityClass = entityClass;
        this.entityId = entityId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public BigInteger getEntityId() {
        return entityId;
    }

    public void setEntityId(BigInteger entityId) {
        this.entityId = entityId;
    }

    public Date getDbTimestamp() {
        return dbTimestamp;
    }

    public void setDbTimestamp(Date dbTimestamp) {
        this.dbTimestamp = dbTimestamp;
    }

    public Boolean getSentToDevice() {
        return sentToDevice;
    }

    public void setSentToDevice(Boolean sentToDevice) {
        this.sentToDevice = sentToDevice;
    }

    public enum ActionName {
        ADDED, DELETED, MODIFIED
    }

}
