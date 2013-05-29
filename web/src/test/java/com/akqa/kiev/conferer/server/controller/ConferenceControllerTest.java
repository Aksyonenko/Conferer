package com.akqa.kiev.conferer.server.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;

import com.akqa.kiev.conferer.server.matchers.IsoDateFormatMatcher;

public class ConferenceControllerTest extends AbstractControllerTest {

	@Test
	public void conferences_existingConferences() throws Exception {
		preCheckJsonResponse("/conferences?year=2013&month=1")
			.andExpect(jsonPath("$[*]", hasSize(3)))
			.andExpect(jsonPath("$[*].id", contains("CONF_1", "CONF_2", "CONF_3")))
			.andExpect(jsonPath("$[*].conferenceUrl", everyItem(is("dummy conference url"))))
			.andExpect(jsonPath("$[*].title", everyItem(not(isEmptyOrNullString()))))
			.andExpect(jsonPath("$[*].startDate", everyItem(new IsoDateFormatMatcher())))
			.andExpect(jsonPath("$[*].endDate", everyItem(new IsoDateFormatMatcher())))
			.andExpect(jsonPath("$[*].summary", everyItem(not(isEmptyOrNullString()))))
			.andExpect(jsonPath("$[*].country", contains("Japan", "China", "Ukraine")))
			.andExpect(jsonPath("$[*].region", contains("Kanto", "North China", null)))
			.andExpect(jsonPath("$[*].city", contains("Tokyo", "Beijing", "Kiev")))
			.andExpect(jsonPath("$[*].address", everyItem(is("dummy address"))))
			.andExpect(jsonPath("$[*].logoUrl", everyItem(is("dummy logo url"))))
			.andExpect(jsonPath("$[*].details").doesNotExist())
			.andExpect(jsonPath("$[*].sessions").doesNotExist());
	}
	
	@Test
	public void conferences_nonexistingConferences() throws Exception {
		preCheckJsonResponse("/conferences?year=2012&month=1")
			.andExpect(jsonPath("$[*]", empty()));
	}
	
	@Test
	public void conference_existingConference() throws Exception {
		preCheckJsonResponse("/conferences/CONF_1")
			.andExpect(jsonPath("$.id", is("CONF_1")))
			.andExpect(jsonPath("$.conferenceUrl", is("dummy conference url")))
			.andExpect(jsonPath("$.title", is("QCon New York 2013")))
			.andExpect(jsonPath("$.startDate", new IsoDateFormatMatcher()))
			.andExpect(jsonPath("$.endDate", new IsoDateFormatMatcher()))
			.andExpect(jsonPath("$.summary", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.country", is("Japan")))
			.andExpect(jsonPath("$.region", is("Kanto")))
			.andExpect(jsonPath("$.city", is("Tokyo")))
			.andExpect(jsonPath("$.address", is("dummy address")))
			.andExpect(jsonPath("$.logoUrl", is("dummy logo url")))
			.andExpect(jsonPath("$.details", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.sessions", not(empty())));
	}
	
	@Test
	public void conference_months() throws Exception {
		preCheckJsonResponse("/conferences/months")
			.andExpect(jsonPath("$[*]", contains("")));
	}
}
