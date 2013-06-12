
package com.akqa.kiev.conferer.server.dao.mongo;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.akqa.kiev.conferer.server.dao.ConferenceCustomDao;
import com.akqa.kiev.conferer.server.model.Conference;

public class ConferenceDaoMongo implements ConferenceCustomDao {

    public static final String CONFERENCE_COLLECTION = "conferences";

    @Autowired
    private MongoTemplate template;
    
    // @Override
    public List<Conference> find(Date fromDate, Date toDate) {
    	throw new UnsupportedOperationException();
        /*Criteria innerConference = where("startDate").gte(fromDate).and("endDate").lte(toDate);
        Criteria outerConference = where("startDate").lte(fromDate).and("endDate").gte(toDate);
        Criteria finishingConference = where("startDate").lte(fromDate).and("endDate").gte(fromDate).lte(toDate);
        Criteria beginningConference = where("startDate").gte(fromDate).lte(toDate).and("endDate").gte(toDate);

        Criteria fullCriteria = new Criteria();
        fullCriteria.orOperator(innerConference, outerConference, finishingConference, beginningConference);

        Query mongoQuery = query(fullCriteria);
        
        for (String field : new String[] {"conferenceID", "conferenceURL", "title", "startDate", "endDate", "summary", "country", "region", "city", "address", "logoURL"}) {
        	mongoQuery.fields().include(field);
        }
        
        return template.find(mongoQuery, Conference.class);*/
    }

	@Override
	public List<Conference> findByMonthAndYear(int year, int month) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Long> findActiveMonths() {
		throw new UnsupportedOperationException();
	}

}
