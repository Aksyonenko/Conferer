package com.akqa.kiev.conferer.server;

import java.util.Date;
import java.util.List;

import com.akqa.kiev.conferer.Conference;

public interface ConferenceDao {
	List<Conference> getConferences(Date fromDate, Date toDate);
}
