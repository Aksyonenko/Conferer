package com.akqa.kiev.conferer;

import java.util.Date;
import java.util.List;

public class Session {
	
	public enum Type {
		Tutorial, Workshop, OpenDiscussion, Presentation
	}
	
	private String id;
	private String title;
	private String summary;
	private Date startTime;
	private Date endTime;
	
	private List<Speaker> speakers;
}
