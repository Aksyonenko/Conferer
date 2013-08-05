package com.akqa.kiev.conferer.server.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "speakers")
public class Speaker extends AbstractEntity {

	@Column(length = 1024)
	private String speakerUrl;
	
	@Column(length = 64)
	private String firstName;
	
	@Column(length = 64)
	private String lastName;

    @Column(length = 64)
    private String competence;
	
	@Column(length = 1024)
	private String photoUrl;
	
	@Column(length = 4096)
	private String about;

	@Embedded
	private SocialLinks socialLinks = new SocialLinks();

	public Speaker() {
		
	}
	
	public String getSpeakerUrl() {
		return speakerUrl;
	}

	public void setSpeakerUrl(String speakerUrl) {
		this.speakerUrl = speakerUrl;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public SocialLinks getSocialLinks() {
		return socialLinks;
	}

	public void setSocialLinks(SocialLinks socialLinks) {
		this.socialLinks = socialLinks;
	}

}
