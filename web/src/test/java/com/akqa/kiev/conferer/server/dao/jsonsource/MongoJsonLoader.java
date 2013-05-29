package com.akqa.kiev.conferer.server.dao.jsonsource;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.akqa.kiev.conferer.server.dao.jsonsource.mixin.BasicMixin;
import com.akqa.kiev.conferer.server.dao.jsonsource.mixin.ConferenceMixin;
import com.akqa.kiev.conferer.server.dao.jsonsource.mixin.SessionMixIn;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

@Component
public class MongoJsonLoader {

	@Autowired
	private ApplicationContext applicationContext;
	
	private final Map<String, Conference> conferences = new HashMap<>();
	private final Map<String, Session> sessions = new HashMap<>();
	private final Map<String, Speaker> speakers = new HashMap<>();
	
	@PostConstruct
	public void loadFromJson() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.addMixInAnnotations(Session.class, SessionMixIn.class);
			objectMapper.addMixInAnnotations(Speaker.class, BasicMixin.class);
			objectMapper.addMixInAnnotations(Conference.class, ConferenceMixin.class);
			
			Resource jsonResource = applicationContext.getResource("classpath:/mongo/integration-testing.js");
			JsonNode fullJson = objectMapper.readTree(jsonResource.getInputStream());
			
			JsonNode speakersNode = fullJson.findValue("speakers");
			MappingIterator<Speaker> speakersIteration = objectMapper.reader(Speaker.class).readValues(speakersNode.toString());
			
			while (speakersIteration.hasNext()) {
				Speaker speaker = speakersIteration.next();
				speakers.put(speaker.getId(), speaker);
			}
			
			String conferencesJson = fullJson.findValue("conferences").toString();
			MappingIterator<Conference> conferencesIteration = objectMapper.reader(Conference.class).readValues(conferencesJson);
			
			while (conferencesIteration.hasNext()) {
				Conference conference = conferencesIteration.next();
				conferences.put(conference.getId(), conference);
				
//				Object tz = JsonPath.<Object>read(conferencesJson, "$.[?].timezone",
//						filter(where("_id").is(conference.getId())));
//				conference.setTimezone(null);
				
				conference.setStartDate(parseDate(conferencesJson, "$[?].startDate['$date']",
					filter(where("_id").is(conference.getId()))));
				
				conference.setEndDateTime(parseDate(conferencesJson, "$[?].endDate['$date']",
					filter(where("_id").is(conference.getId()))));
				
				for (Session session : conference.getSessions()) {
					sessions.put(session.getId(), session);
					
					List<String> speakerRefs = JsonPath.<List<String>>read(conferencesJson, "$.[?].sessions[?].speakers[*]['$id']",
						filter(where("_id").is(conference.getId())),
						filter(where("_id").is(session.getId())));
					
					session.setSpeakers(new ArrayList<Speaker>(speakerRefs.size()));
					for (String speakerId : speakerRefs) {
						session.getSpeakers().add(speakers.get(speakerId));
					}
					
					session.setTimezone(conference.getTimezone());
					
					session.setStartTime(parseDate(conferencesJson, "$.[?].sessions[?].startTime['$date']", 
						filter(where("_id").is(conference.getId())),
						filter(where("_id").is(session.getId()))));
					
					session.setEndTime(parseDate(conferencesJson, "$.[?].sessions[?].endTime['$date']", 
						filter(where("_id").is(conference.getId())),
						filter(where("_id").is(session.getId()))));
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return the conferences
	 */
	public Map<String, Conference> getConferences() {
		return conferences;
	}

	/**
	 * @return the sessions
	 */
	public Map<String, Session> getSessions() {
		return sessions;
	}

	/**
	 * @return the speakers
	 */
	public Map<String, Speaker> getSpeakers() {
		return speakers;
	}

	private static Date parseDate(String json, String jsonPath, Filter<?>... filters) {
		List<Long> startDate = JsonPath.<List<Long>>read(json, jsonPath, filters);
		return new Date(startDate.get(0));
	}
}
