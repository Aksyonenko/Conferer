package com.akqa.kiev.conferer.server.dao.config.sql;

import java.io.File;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@Profile("test")
public class H2InMemoryConfig extends H2EmbeddedConfig {

	private static final Logger log = LoggerFactory.getLogger(H2InMemoryConfig.class);
	
	@Override
	@Bean
	public EmbeddedDatabase dataSource() {
//		String id = new BigInteger(32, new Random()).toString(32).toUpperCase();
		String id = UUID.randomUUID().toString();
		log.info("Initializating in-memory H2 datasource with random DB name of '{}'", id);
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase database = builder.setType(EmbeddedDatabaseType.H2).setName(id).addScript("jpa/schema.sql").addScript("jpa/data.sql").build();
		
		/*JdbcDataSource database = new JdbcDataSource();
		database.setURL("jdbc:h2:" + System.getProperty("user.home") + File.separator + ".conferer" + File.separator + "database");
		database.setUser("sa");
		database.setPassword("sa");
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jpa/schema.sql"));
		populator.addScript(new ClassPathResource("jpa/data.sql"));
		try {
			populator.populate(database.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}*/
		
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
