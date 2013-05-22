package com.akqa.kiev.conferer.server.dao.config;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.minidev.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.dao.jsonmock.MockConferenceDao;
import com.akqa.kiev.conferer.server.dao.jsonmock.mixin.BasicMixin;
import com.akqa.kiev.conferer.server.dao.jsonmock.mixin.ConferenceMixin;
import com.akqa.kiev.conferer.server.dao.jsonmock.mixin.SessionMixIn;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

@Profile("json-mock")
@ComponentScan(basePackages = "com.akqa.kiev.conferer.server.dao.mock")
@Configuration
public class JsonMockTestConfig {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		objectMapper.addMixInAnnotations(Session.class, SessionMixIn.class);
		objectMapper.addMixInAnnotations(Speaker.class, BasicMixin.class);
		objectMapper.addMixInAnnotations(Conference.class, ConferenceMixin.class);
		this.objectMapper = objectMapper;
	}
	
	@Bean
	public ConferenceDao conferenceDao(Resource resource) {
		return new MockConferenceDao();
	}
	
	@PostConstruct
	public void loadFromJson() {
		try {
			Resource jsonResource = applicationContext.getResource("classpath:/mongo/integration-testing.js");
			JsonNode fullJson = objectMapper.readTree(jsonResource.getInputStream());
			
			JsonNode speakersNode = fullJson.findValue("speakers");
			MappingIterator<Speaker> speakersIteration = objectMapper.reader(Speaker.class).readValues(speakersNode.toString());
			Map<String, Speaker> speakers = new HashMap<>();
			
			while (speakersIteration.hasNext()) {
				Speaker speaker = speakersIteration.next();
				speakers.put(speaker.getId(), speaker);
			}
			
			String conferencesJson = fullJson.findValue("conferences").toString();
			MappingIterator<Conference> conferencesIteration = objectMapper.reader(Conference.class).readValues(conferencesJson);
			
			Map<String, Conference> conferences = new HashMap<>();
			while (conferencesIteration.hasNext()) {
				Conference conference = conferencesIteration.next();
				conferences.put(conference.getId(), conference);
				
				conference.setStartDate(parseDate(conferencesJson, "$.[?].startDate['$date']",
					filter(where("_id").is(conference.getId()))));
				
				conference.setEndDate(parseDate(conferencesJson, "$.[?].endDate['$date']",
						filter(where("_id").is(conference.getId()))));
				
				for (Session session : conference.getSessions()) {
					List<String> speakerRefs = JsonPath.<List<String>>read(conferencesJson, "$.[?].sessions[?].speakers[*]['$id']",
						filter(where("_id").is(conference.getId())),
						filter(where("_id").is(session.getId())));
					
					session.setSpeakers(new ArrayList<Speaker>(speakerRefs.size()));
					for (String speakerId : speakerRefs) {
						session.getSpeakers().add(speakers.get(speakerId));
					}
					
					session.setStartTime(parseDate(conferencesJson, "$.[?].sessions[?].startTime['$date']", 
						filter(where("_id").is(conference.getId())),
						filter(where("_id").is(session.getId()))));
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Date parseDate(String json, String jsonPath, Filter<?>... filters) {
		List<Long> startDate = JsonPath.<List<Long>>read(json, jsonPath, filters);
		return new Date(startDate.get(0));
	}
}