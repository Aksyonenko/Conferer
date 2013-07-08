package com.akqa.kiev.conferer.server.dao.h2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.akqa.kiev.conferer.server.model.Speaker;


public class HibernateTest {

	private EntityManagerFactory emf;
	private JdbcDataSource dataSource;
	
	@Before
	public void setUp() throws SQLException {
		emf = Persistence.createEntityManagerFactory("conferer");
		
		dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test");
		dataSource.setUser("sa");
		dataSource.setPassword("sa");
		
		Connection connection = dataSource.getConnection();
		
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		
		Resource initScript = new ClassPathResource("jpa/schema.sql");
		populator.addScript(initScript);
		
		Resource dataScript = new ClassPathResource("jpa/data.sql");
		populator.addScript(dataScript);
		
		populator.populate(connection);
		connection.close();
	}
	
	@Test
	public void test() {
		EntityManager em = emf.createEntityManager();
		
		Speaker speaker = new Speaker();
		speaker.setFirstName("Test");
		speaker.setLastName("Test");
		
		em.getTransaction().begin();
		em.persist(speaker); 
		em.flush();
		em.getTransaction().commit();
	}
	
	@After
	public void tearDown() throws SQLException {
		emf.close();

		Connection connection = dataSource.getConnection();
		Statement stmt = connection.createStatement();
		stmt.execute("SHUTDOWN");
	}
}
