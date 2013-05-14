package com.akqa.kiev.conferer.server.dao;

import java.util.Date;
import java.util.List;

import com.akqa.kiev.conferer.server.model.Conference;

public interface ConferenceCustomDao {
	List<Conference> find(Date fromDate, Date toDate);
}
