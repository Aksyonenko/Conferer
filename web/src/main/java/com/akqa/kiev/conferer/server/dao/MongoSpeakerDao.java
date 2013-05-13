
package com.akqa.kiev.conferer.server.dao;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import com.akqa.kiev.conferer.Speaker;
import com.akqa.kiev.conferer.server.SpeakerDao;

public class MongoSpeakerDao extends AbstractMongoDao<Speaker> implements SpeakerDao {

    private static final String SPEAKERS_COLLECTION = "speakers";

    public MongoSpeakerDao(MongoTemplate template) {
        super(template);
    }

    @Override
    public Speaker getSpeakerById(String speakerId) {
        Criteria speakerById = where("speakerId").is(speakerId);
        List<Speaker> speakers = find(query(speakerById), SPEAKERS_COLLECTION);
        return speakers != null && !speakers.isEmpty() ? speakers.get(0) : null;
    }
}
