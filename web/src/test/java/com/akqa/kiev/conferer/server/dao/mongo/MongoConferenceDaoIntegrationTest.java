package com.akqa.kiev.conferer.server.dao.mongo;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
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
import com.akqa.kiev.conferer.server.model.Session;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;


@UsingDataSet(locations = "/mongo/integration-testing.js", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class MongoConferenceDaoIntegrationTest extends AbstractMongoDaoIntegrationTest {

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
	public void findOne_existingConference() {
		Conference conference = conferenceDao.findOne(BigInteger.valueOf(1));
		assertThat(conference, notNullValue());
		
		assertThat(conference.getId(), is(BigInteger.valueOf(1)));
		assertThat(conference.getConferenceUrl(), is("dummy conference url"));
		assertThat(conference.getLogoUrl(), is("dummy logo url"));
		assertThat(conference.getTitle(), is("QCon New York 2013"));
		assertThat(conference.getSummary(), notNullValue());
		// assertThat(conference.getStartDate(), is(new Date(1357084800000L)));
		// assertThat(conference.getEndDate(), is(new Date(1357344000000L)));
		assertThat(conference.getCountry(), is("Japan"));
		assertThat(conference.getRegion(), is("Kanto"));
		assertThat(conference.getCity(), is("Tokyo"));
		assertThat(conference.getAddress(), is("dummy address"));
		assertThat(conference.getDetails(), notNullValue());

		assertThat(conference.getSessions(), hasSize(4));
		Session session;
		
		session = conference.getSessions().get(0);
		assertThat(session.getId(), is(BigInteger.valueOf(1)));
		assertThat(session.getTitle(), is("Avoiding Invisible Impediments to High Performance"));
		assertThat(session.getSummary(), notNullValue());
		// assertThat(session.getStartTime(), is(new Date(1357117200000L)));
		// assertThat(session.getEndTime(), is(new Date(1357124400000L)));
		assertThat(session.getType(), is("Workshop"));
		
		assertThat(session.getSpeakers(), hasSize(1));
		assertThat(session.getSpeakers().get(0).getId(), is(BigInteger.valueOf(1)));
	}
	
	@Test
	public void findOne_nonExistingConference() {
		Conference conference = conferenceDao.findOne(BigInteger.valueOf(-1));
		assertThat(conference, nullValue());
	}
	
	@Test
	public void find_noConferences() throws ParseException {
		dateTest("31.12.2012", "01.01.2013", 0);
	}
	
	@Test
	public void find_innerConferences() throws ParseException {
		dateTest("11.01.2013", "21.01.2013", 1);
		dateTest("01.01.2013", "10.01.2013", 2);
		dateTest("02.01.2013", "20.01.2013", 3);
	}
	
	@Test
	public void find_outerConferences() throws ParseException {
		dateTest("06.01.2013", "09.01.2013", 1);
		dateTest("04.01.2013", "04.01.2013", 2);
	}
	
	@Test
	public void find_finishingConferences() throws ParseException {
		dateTest("15.01.2013", "25.01.2013", 1);
	}
	
	@Test
	public void find_beginningConferences() throws ParseException {
		dateTest("12.01.2013", "15.01.2013", 1);
	}
	
	@Test
	public void find_combinedConferences() throws ParseException {
		dateTest("10.01.2013", "15.01.2013", 2);
		
	}
	
	private void dateTest(String startDate, String endDate, int expectedSize) throws ParseException {
		Date from = dateFormat.parse(startDate + " 00:00:00");
		Date to = dateFormat.parse(endDate + " 00:00:00");
		
		List<Conference> conferences = null; //conferenceDao.find(from, to);
		assertThat(conferences, hasSize(expectedSize));
		
		for (Conference conference : conferences) {
			assertThat(conference.getSessions(), nullValue());
			assertThat(conference.getDetails(), nullValue());
		}
	}
}
