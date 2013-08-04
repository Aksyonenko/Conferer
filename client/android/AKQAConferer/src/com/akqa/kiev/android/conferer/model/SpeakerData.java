package com.akqa.kiev.android.conferer.model;

public class SpeakerData {

	private Long id;
	private String speakerUrl;
	private String firstName;
	private String lastName;
	private String competence;
	private String photoUrl;
	private String about;
	private SocialLinksData socialLinks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SocialLinksData getSocialLinks() {
		return socialLinks;
	}

	public void setSocialLinks(SocialLinksData socialLinks) {
		this.socialLinks = socialLinks;
	}

}
