package com.akqa.kiev.conferer.server.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.akqa.kiev.conferer.server.model.Conference;

public interface ConferenceDao extends AbstractDao<Conference>, ConferenceCustomDao {
	
	@Query(fields = "{" +
			"'conferenceID' : 1, " +
			"'conferenceUrl' : 1, " +
			"'title' : 1, " +
			"'startDate' : 1, " +
			"'endDate' : 1, " +
			"'summary' : 1, " +
			"'country' : 1, " +
			"'region' : 1, " +
			"'city' : 1, " +
			"'address' : 1, " +
			"'logoUrl' : 1" +
		"}")
	List<Conference> findAll();
}
