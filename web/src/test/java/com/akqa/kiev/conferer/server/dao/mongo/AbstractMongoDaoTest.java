package com.akqa.kiev.conferer.server.dao.mongo;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.akqa.kiev.conferer.server.dao.config.MongoTestConfig;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { MongoTestConfig.class })
public abstract class AbstractMongoDaoTest {
	
	@Rule
	public MongoDbRule mongoDbRule = MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb("test");
}
