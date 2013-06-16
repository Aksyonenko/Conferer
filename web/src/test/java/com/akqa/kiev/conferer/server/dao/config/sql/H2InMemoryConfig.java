package com.akqa.kiev.conferer.server.dao.config.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("test")
public class H2InMemoryConfig extends H2EmbeddedConfig {

	private static final Logger log = LoggerFactory.getLogger(H2InMemoryConfig.class);
	
	@Override
	@Bean
	public EmbeddedDatabase dataSource() throws SQLException {
		String id = UUID.randomUUID().toString();
		log.info("Initializating in-memory H2 datasource with random DB name of '{}'", id);
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase database = builder.setType(EmbeddedDatabaseType.H2).setName(id).addScript("jpa/schema.sql").addScript("jpa/data.sql").build();
		
		Connection connection = database.getConnection();
		registerAlias(connection);
		connection.close();
		
		return database;
	}
	
	@Autowired
	public Properties updateJpaProperties(Properties jpaProperties) {
		if (log.isDebugEnabled()) log.debug("Enriching JPA properties with additional parameters");
		
		// properties.setProperty("hibernate.hbm2ddl.auto", "create");
		// jpaProperties.setProperty("hibernate.show_sql", "true");

		return jpaProperties;
	}
}
