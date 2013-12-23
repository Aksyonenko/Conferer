package com.akqa.kiev.android.conferer.model;

public class SearchData {
	public static final int TYPE_CONFERENCE = 0;
	public static final int TYPE_SESSION = 1;
	public static final int TYPE_SPEAKER = 2;
	
	private Long id;
	private String title;
	private String subtitle;
	private int type;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
