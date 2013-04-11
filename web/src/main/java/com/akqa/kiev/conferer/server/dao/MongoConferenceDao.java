package com.akqa.kiev.conferer.server.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import com.akqa.kiev.conferer.Conference;
import com.akqa.kiev.conferer.server.ConferenceDao;

public class MongoConferenceDao implements ConferenceDao {

	private static final String CONFERENCE_COLLECTION = "conferences";
	
	private final MongoTemplate template;

	public MongoConferenceDao(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public List<Conference> getConferences(Date fromDate, Date toDate) {
		Criteria innerConference = where("startDate").gte(fromDate).and("endDate").lte(toDate);
		Criteria outerConference = where("startDate").lte(fromDate).and("endDate").gte(toDate);
		Criteria finishingConference = where("startDate").lte(fromDate).and("endDate").gte(fromDate).lte(toDate);
		Criteria beginningConference = where("startDate").gte(fromDate).lte(toDate).and("endDate").gte(toDate);
		
		Criteria fullCriteria = new Criteria();
		fullCriteria.orOperator(innerConference, outerConference, finishingConference, beginningConference);
		
		List<Conference> conferences = template.find(query(fullCriteria), Conference.class, CONFERENCE_COLLECTION);
		return conferences;
	}

}
