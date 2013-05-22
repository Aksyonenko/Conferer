package com.akqa.kiev.conferer.server.dao.mongo;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;

@UsingDataSet(locations = "/mongo/integration-testing.js", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class MongoSpeakersDaoIntegrationTest extends AbstractMongoDaoIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SpeakerDao speakerDao;

	@Test
	public void findOne_existingSpeaker() {
		Speaker speaker = speakerDao.findOne("SP_1");
		assertThat(speaker, notNullValue());

		assertThat(speaker.getFirstName(), is("Adam"));
		assertThat(speaker.getLastName(), is("Blum"));
		assertThat(speaker.getSpeakerUrl(), is("some url"));
		assertThat(speaker.getPhotoUrl(), is("some photo url"));
		assertThat(speaker.getSocialLinks().getFacebook(), is("facebook link"));
		assertThat(speaker.getSocialLinks().getLinkedin(), is("linkedin link"));
		assertThat(speaker.getSocialLinks().getTwitter(), is("twitter link"));

		for (int i = 2; i <= 15; i++) {
			speaker = speakerDao.findOne("SP_" + i);
			assertThat(speaker, notNullValue());
			assertThat(speaker.getFirstName(), notNullValue());
			assertThat(speaker.getLastName(), notNullValue());
			assertThat(speaker.getAbout(), notNullValue());
			assertThat(speaker.getSocialLinks(), notNullValue());
		}
	}

	@Test
	public void findOne_notExistingSpeaker() {
		Speaker speaker = speakerDao.findOne("not_exist");
		assertThat(speaker, nullValue());
	}
}
