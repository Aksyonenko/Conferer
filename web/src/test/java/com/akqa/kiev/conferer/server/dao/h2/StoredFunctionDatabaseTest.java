package com.akqa.kiev.conferer.server.dao.h2;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.akqa.kiev.conferer.server.dao.config.JpaConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = JpaConfig.class)
public class StoredFunctionDatabaseTest extends StoredFunctionTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected Boolean checkFunction(int month, int year, Date eventStart, Date eventEnd, String timezoneId) {
		try {
			CallableStatement statement = dataSource.getConnection().prepareCall("? = CALL HAPPENS_IN_MONTH_YEAR(?, ?, ?, ?, ?)");
			int i = 0;
			statement.registerOutParameter(++i, Types.BOOLEAN);
			statement.setInt(++i, month);
			statement.setInt(++i, year);
			
			java.sql.Date start = new java.sql.Date(eventStart.getTime());
			java.sql.Date end = new java.sql.Date(eventEnd.getTime());
			statement.setDate(++i, start);
			statement.setDate(++i, end);
			statement.setString(++i, timezoneId);
			
			statement.execute();
			
			return statement.getBoolean(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
