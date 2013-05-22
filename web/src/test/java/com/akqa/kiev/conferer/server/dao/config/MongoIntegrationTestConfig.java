package com.akqa.kiev.conferer.server.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("integration-test")
@ComponentScan(basePackages = "com.akqa.kiev.conferer.server.dao.mongo")
@Configuration
public class MongoIntegrationTestConfig extends MongoConfig {
	
	@Override
	protected String getDatabaseName() {
		return "test";
	}

}
