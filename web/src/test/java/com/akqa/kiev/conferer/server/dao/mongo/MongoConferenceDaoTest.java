package com.akqa.kiev.conferer.server.dao.mongo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;


@UsingDataSet(locations = "/mongo/integration-testing.js", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class MongoConferenceDaoTest extends AbstractMongoDaoTest {

	private static final DateFormat dateFormat;
	
	static {
		dateFormat = new SimpleDateFormat("dd.mm.yyyy hh:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ConferenceDao conferenceDao;
	
	@Test
	public void getConferencesTest_noConferences() throws ParseException {
		dateTest("31.12.2012", "01.01.2013", 0);
	}
	
	@Test
	public void getConferencesTest_innerConferences() throws ParseException {
		dateTest("11.01.2013", "21.01.2013", 1);
		dateTest("01.01.2013", "10.01.2013", 2);
		dateTest("02.01.2013", "20.01.2013", 3);
	}
	
	@Test
	public void getConferencesTest_outerConferences() throws ParseException {
		dateTest("06.01.2013", "09.01.2013", 1);
		dateTest("04.01.2013", "04.01.2013", 2);
	}
	
	@Test
	public void getConferencesTest_finishingConferences() throws ParseException {
		dateTest("15.01.2013", "25.01.2013", 1);
	}
	
	@Test
	public void getConferencesTest_beginningConferences() throws ParseException {
		dateTest("12.01.2013", "15.01.2013", 1);
	}
	
	@Test
	public void getConferencesTest_combinedConferences() throws ParseException {
		dateTest("10.01.2013", "15.01.2013", 2);
		
	}
	
	private void dateTest(String startDate, String endDate, int expectedSize) throws ParseException {
		Date from = dateFormat.parse(startDate + " 00:00:00");
		Date to = dateFormat.parse(endDate + " 00:00:00");
		
		List<Conference> conferences = conferenceDao.find(from, to);
		assertThat(conferences, hasSize(expectedSize));
		
		for (Conference conference : conferences) {
			assertThat(conference.getSessions(), nullValue());
		}
	}
}
