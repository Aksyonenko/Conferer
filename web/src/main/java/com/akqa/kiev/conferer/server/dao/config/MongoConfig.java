package com.akqa.kiev.conferer.server.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.mongodb.Mongo;

@Configuration
@Profile({"mongo-local", "mongo-prod"})
@ComponentScan(basePackages = "com.akqa.kiev.conferer.server.dao.mongo")
@EnableMongoRepositories(
	value = "com.akqa.kiev.conferer.server.dao",
	repositoryImplementationPostfix = "Mongo",
	excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = SessionDao.class)
)
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "conferer";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new Mongo();
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.akqa.kiev.conferer.server.model";
	}
}
