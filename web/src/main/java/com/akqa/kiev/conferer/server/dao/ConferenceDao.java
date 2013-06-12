package com.akqa.kiev.conferer.server.dao;

import java.util.List;

import com.akqa.kiev.conferer.server.model.Conference;

public interface ConferenceDao extends AbstractDao<Conference>, ConferenceCustomDao {
	
	List<Conference> findAll();
}
