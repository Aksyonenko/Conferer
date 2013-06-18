package com.akqa.kiev.conferer.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SocialLinks {

	@Column(length = 128)
	private String facebook;
	
	@Column(length = 32)
	private String twitter;
	
	@Column(length = 255)
	private String linkedin;

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

}
