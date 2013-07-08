package com.akqa.kiev.conferer.server.dao.h2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.dao.config.JpaConfig;
import com.akqa.kiev.conferer.server.model.Speaker;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
//@TransactionConfiguration
@ContextConfiguration(classes = JpaConfig.class)
public class JpaRepositoryTest {

	@Autowired
	private SpeakerDao dao;
	
	@Autowired
	private PlatformTransactionManager tm;
	
	@Test
	public void speakerDao_save() {
		final Speaker entity = new Speaker();
		entity.setFirstName("1");
		entity.setLastName("2");
		
		//TransactionTemplate template = new TransactionTemplate(tm);
		//template.execute(new TransactionCallbackWithoutResult() {
			
		//	@Override
		//	protected void doInTransactionWithoutResult(TransactionStatus status) {
				dao.save(entity);
		//	}
		//});
	}
}
