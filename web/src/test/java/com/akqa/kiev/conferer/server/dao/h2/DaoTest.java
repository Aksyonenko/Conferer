package com.akqa.kiev.conferer.server.dao.h2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.dao.config.JpaConfig;
import com.akqa.kiev.conferer.server.model.Speaker;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = JpaConfig.class)
public class DaoTest {

	@Autowired
	private SpeakerDao speakerDao;
	
	@Test
	public void speakerDao_save() {
		Speaker entity = new Speaker();
		entity.setFirstName("1");
		entity.setLastName("2");
		
		speakerDao.save(entity);
	}
}
