package com.akqa.kiev.conferer.server.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.dao.jsonsource.ConferenceDaoPreloaded;
import com.akqa.kiev.conferer.server.dao.jsonsource.MongoJsonLoader;
import com.akqa.kiev.conferer.server.dao.jsonsource.SessionDaoPreloaded;
import com.akqa.kiev.conferer.server.dao.jsonsource.SpeakerDaoPreloaded;

@Profile("mongo-test")
@ComponentScan(basePackages = "com.akqa.kiev.conferer.server.dao.jsonsource")
@Configuration
public class MongoJsonSourceConfig {
	
	@Bean
	public ConferenceDao conferenceDao(MongoJsonLoader loader) {
		return new ConferenceDaoPreloaded(loader.getConferences());
	}
	
	@Bean
	public SpeakerDao speakerDao(MongoJsonLoader loader) {
		return new SpeakerDaoPreloaded(loader.getSpeakers());
	}
	
	@Bean
	public SessionDao sessionDao(MongoJsonLoader loader) {
		return new SessionDaoPreloaded(loader.getSessions());
	}
}