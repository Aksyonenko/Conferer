package com.akqa.kiev.conferer.server.dao.config.sql;

import java.io.File;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
		
		log.info("Initializating H2 DataSource at {}", databasePath);
		
		if (!new File(databasePath + ".h2.db").exists()) {
			String initScript = "classpath:jpa/schema.sql";
			log.warn("DB doesn't exist. Running init script {}", initScript);
			url = String.format("jdbc:h2:%s;INIT=RUNSCRIPT FROM '%s'", databasePath, initScript);
		} else {
			url = "jdbc:h2:" + databasePath;
		}
		
		if (log.isDebugEnabled()) log.debug("Using connection string of {}", url);
		
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL(url);
		ds.setUser("sa");
		ds.setPassword("sa");
		
		registerStoredProcedure();
		
		return ds;
	}
	
	protected void registerStoredProcedure() throws SQLException {
		log.info("Registering custom H2 stored procedure {}", StoredFunction.SQL_NAME);
		
		DataSource dataSource = this.dataSource();
		dataSource.getConnection().createStatement().execute("CREATE ALIAS " + StoredFunction.SQL_NAME + " FOR \"com.akqa.kiev.conferer.server.dao.jpa.h2.StoredFunction." + StoredFunction.JAVA_NAME + "\"");
	}
	
	@Autowired
	public void configureJpaVendor(HibernateJpaVendorAdapter jpaVendorAdapter) {
		String dialect = H2ConfererDialect.class.getCanonicalName();
		log.info("Configuring Hibernate JPA adapter to use H2 database with custom dialect of {}", dialect);
		jpaVendorAdapter.setDatabase(Database.H2);
		jpaVendorAdapter.setDatabasePlatform(dialect);
	}
}