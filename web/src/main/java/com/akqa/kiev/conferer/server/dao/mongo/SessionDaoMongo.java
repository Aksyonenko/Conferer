package com.akqa.kiev.conferer.server.dao.mongo;


import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;


@Repository
public class SessionDaoMongo implements SessionDao {

	@Autowired
	private MongoTemplate template;
	
	@Override
	public Session findOne(BigInteger id) throws IncorrectResultSizeDataAccessException {
		Criteria criteria = where("sessions").elemMatch(where("_id").is(id));
		CustomSpringQuery query = new CustomSpringQuery(criteria);
		query.fields().project(criteria.getCriteriaObject());
		
		Conference conference = template.findOne(query, Conference.class);
		
		if (conference == null || conference.getSessions().isEmpty()) return null;
		if (conference.getSessions().size() > 1) throw new IncorrectResultSizeDataAccessException(1, conference.getSessions().size());
		
		return conference.getSessions().get(0);
	}
}
