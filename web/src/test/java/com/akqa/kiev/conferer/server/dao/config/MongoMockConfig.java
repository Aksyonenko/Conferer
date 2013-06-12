package com.akqa.kiev.conferer.server.dao.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;

@Profile("mock")
@Configuration
public class MongoMockConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoMockConfig.class);
	
	@Bean
	public ConferenceDao conferenceDao() {
		ConferenceDao conferenceDao = mock(ConferenceDao.class);
		
		Calendar start = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		start.set(Calendar.YEAR, 2013);
		start.set(Calendar.MONTH, 0); // January
		start.set(Calendar.DAY_OF_MONTH, 1);
		
		for (int field : new int[] {Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR_OF_DAY}) {
			start.set(field, 0);
		}
		
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.MONTH, 1);
		end.add(Calendar.DAY_OF_MONTH, -1);
		
		List<Conference> conferences = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			Conference conference = new Conference();
			conference.setId(BigInteger.valueOf(i + 1));
			
			conference.setAddress("dummy address");
			conference.setCity("dummy city");
			conference.setConferenceUrl("dummy conference url");
			conference.setCountry("dummy country");
			
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(1356998400000L); // 1st Jan 2013
				conference.setEndDate(calendar);
			}
			
			conference.setLogoUrl("dummy logo url");
			conference.setRegion("dummy region");
			
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(1357776000000L); // 10th Jan 2013
				conference.setEndDate(calendar);
			}
			
			conference.setSummary("dummy summary");
			conference.setTitle("dummy title");
			
			conferences.add(conference);
			
			logger.info("Mocking ConferenceDao for id = {}", conference.getId());
			Conference fullConference = new Conference();
			BeanUtils.copyProperties(conference, fullConference);
			fullConference.setDetails("dummy details");
			fullConference.setSessions(Arrays.asList(new Session(), new Session(), new Session()));
			when(conferenceDao.findOne(fullConference.getId()))
				.thenReturn(fullConference);
		}

		//when(conferenceDao.find(any(Date.class), any(Date.class)))
		//	.thenThrow(new IllegalArgumentException("Mock ConferenceDao doesn't support supplied arguments"));
		
		logger.info("Mocking ConferenceDao for range [{}, {}]", start.getTime(), end.getTime());
		// when(conferenceDao.find(start.getTime(), end.getTime()))
		//	.thenReturn(conferences);
		
		logger.info("Mocking ConferenceDao for findAll()");
		when(conferenceDao.findAll()).thenReturn(conferences);
		
		return conferenceDao;
	}
	
	@Bean
	public SpeakerDao speakerDao() {
		return mock(SpeakerDao.class);
	}
	
	@Bean
	public SessionDao sessionDao() {
		return mock(SessionDao.class);
	}
}