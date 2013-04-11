package com.akqa.kiev.conferer.server.dao;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.akqa.kiev.conferer.server.ConferenceDao;
import com.mongodb.Mongo;

@Configuration
public class MongoConfig {
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException {
		return new SimpleMongoDbFactory(new Mongo(), "conferer");
	}
	
	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws Exception {
		return new MongoTemplate(mongoDbFactory);
	}
	
	@Bean
	public ConferenceDao conferenceDao(MongoTemplate mongoTemplate) {
		return new MongoConferenceDao(mongoTemplate);
	}
}
