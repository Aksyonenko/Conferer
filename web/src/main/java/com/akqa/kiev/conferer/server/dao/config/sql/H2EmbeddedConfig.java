package com.akqa.kiev.conferer.server.dao.config.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.akqa.kiev.conferer.server.dao.jpa.h2.H2ConfererDialect;
import com.akqa.kiev.conferer.server.dao.jpa.h2.StoredFunction;

@Configuration
@Profile({"local", "prod"})
public class H2EmbeddedConfig {

	private static final Logger log = LoggerFactory.getLogger(H2EmbeddedConfig.class);
	
	@Bean
	public DataSource dataSource() throws SQLException {
		String databasePath = System.getProperty("user.home") + File.separator + ".conferer" + File.separator + "database";
		String url;
		JdbcDataSource ds;
		
		log.info("Initializating H2 DataSource at {}", databasePath);
		
		try {
			url = String.format("jdbc:h2:%s;IFEXISTS=TRUE", databasePath);
			if (log.isDebugEnabled()) log.debug("Checking if DB already exists [{}]", url);

			ds = createJdbcDataSource(url);
			ds.getConnection();
			
			if (log.isDebugEnabled()) log.debug("DB exists, that's good");
			
		} catch (JdbcSQLException e) {
			log.warn("DB doesn't exist. Starting initialization procedure");
			
			url = String.format("jdbc:h2:%s", databasePath);
			ds = createJdbcDataSource(url);
			Connection connection = ds.getConnection();
			
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			
			Resource initScript = new ClassPathResource("jpa/schema.sql");
			log.info("Adding schema script {}", initScript);
			populator.addScript(initScript);
			
			Resource dataScript = new ClassPathResource("jpa/data.sql");
			log.info("Adding data script {}", dataScript);
			populator.addScript(dataScript);
			
			populator.populate(connection);
			registerAlias(connection);
			
			connection.close();
		}
		
		return ds;
	}
	
	protected static void registerAlias(Connection connection) throws SQLException {
		log.info("Registering custom H2 stored procedure {}", StoredFunction.SQL_NAME);
		connection.createStatement().execute("CREATE ALIAS " + StoredFunction.SQL_NAME + " FOR \"com.akqa.kiev.conferer.server.dao.jpa.h2.StoredFunction." + StoredFunction.JAVA_NAME + "\"");
	}
	
	private static JdbcDataSource createJdbcDataSource(String url) {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL(url);
		ds.setUser("sa");
		ds.setPassword("sa");
		
		return ds;
	}
	
	@Autowired
	public void configureJpaVendor(HibernateJpaVendorAdapter jpaVendorAdapter) {
		String dialect = H2ConfererDialect.class.getCanonicalName();
		log.info("Configuring Hibernate JPA adapter to use H2 database with custom dialect of {}", dialect);
		jpaVendorAdapter.setDatabase(Database.H2);
		jpaVendorAdapter.setDatabasePlatform(dialect);
	}
}