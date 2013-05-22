package com.akqa.kiev.conferer.server.dao.mongo;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;

@UsingDataSet(locations = "/mongo/integration-testing.js", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class MongoSessionsDaoIntegrationTest extends AbstractMongoDaoIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SessionDao sessionDao;

	@Test
	public void findOne_existingSession() {
		Session session = sessionDao.findOne("SESSION_1");
		assertThat(session, notNullValue());

		assertThat(session.getId(), is("SESSION_1"));
		assertThat(session.getTitle(), is("Avoiding Invisible Impediments to High Performance"));
		assertThat(session.getSummary(), notNullValue());
		assertThat(session.getStartTime(), is(new Date(1357117200000L)));
		assertThat(session.getEndTime(), is(new Date(1357124400000L)));
		assertThat(session.getType(), is("Workshop"));
		
		assertThat(session.getSpeakers(), hasSize(1));
		Speaker speaker = session.getSpeakers().get(0);
		assertThat(speaker.getId(), is("SP_1"));
		assertThat(speaker.getFirstName(), is("Adam"));
		assertThat(speaker.getLastName(), is("Blum"));
		assertThat(speaker.getSpeakerUrl(), is("some url"));
		assertThat(speaker.getPhotoUrl(), is("some photo url"));
		assertThat(speaker.getSocialLinks().getFacebook(), is("facebook link"));
		assertThat(speaker.getSocialLinks().getLinkedin(), is("linkedin link"));
		assertThat(speaker.getSocialLinks().getTwitter(), is("twitter link"));
	}

	@Test
	public void findOne_nonExistingSession() {
		Session session = sessionDao.findOne("not_exist");
		assertThat(session, nullValue());
	}
}
