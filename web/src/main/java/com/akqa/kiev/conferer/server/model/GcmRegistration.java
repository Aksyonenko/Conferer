
package com.akqa.kiev.conferer.server.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gcm_registrations")
public class GcmRegistration {

    @Id
    @Column(name = "registration_id")
    private String registrationId;

    @Column(nullable = false)
    private Date created;
    
    public GcmRegistration() {

    }

    public GcmRegistration(String registrationId) {
        this.registrationId = registrationId;
        this.created = new Date();
    }
    
    public GcmRegistration(String registrationId, Date created) {
        this.registrationId = registrationId;
        this.created = created;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
