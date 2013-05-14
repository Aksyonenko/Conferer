package com.akqa.kiev.conferer.server.controller;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.akqa.kiev.conferer.server.dao.mongo.AbstractMongoDaoTest;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;

@UsingDataSet(locations = "/mongo/integration-testing.js", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class ConferenceControllerTest extends AbstractMongoDaoTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	private ServletContext servletContext;
	
	@Before
	public void setUp() {
		
	}
}
