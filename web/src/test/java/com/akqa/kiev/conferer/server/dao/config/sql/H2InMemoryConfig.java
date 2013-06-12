package com.akqa.kiev.conferer.server.dao.config.sql;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
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

	@Override
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase database = builder.setType(EmbeddedDatabaseType.H2).addScript("jpa/schema.sql").addScript("jpa/data.sql").build();
		
		/*JdbcDataSource database = new JdbcDataSource();
		database.setURL("jdbc:h2:d:/Data/temp/conferer");
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
}
