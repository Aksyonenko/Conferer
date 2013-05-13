
package com.akqa.kiev.conferer;



public class Speaker {
    
    private String speakerId;
    private String speakerUrl;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String about;
    
    private SocialLinks socialLinks;

    public String getSpeakerId() {
        return speakerId;
    }
    
    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
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
