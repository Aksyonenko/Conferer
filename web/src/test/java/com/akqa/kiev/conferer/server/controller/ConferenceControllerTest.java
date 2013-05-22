package com.akqa.kiev.conferer.server.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;

import com.akqa.kiev.conferer.server.matchers.IsoDateFormatMatcher;

public class ConferenceControllerTest extends AbstractControllerTest {

	@Test
	public void conferences_existingConferences() throws Exception {
		preCheckJsonResponse("/conferences?year=2013&month=1")
			.andExpect(jsonPath("$[*]", hasSize(3)))
			.andExpect(jsonPath("$[0].id").value("CONF_1"))
			.andExpect(jsonPath("$[1].id").value("CONF_2"))
			.andExpect(jsonPath("$[2].id").value("CONF_3"))
			.andExpect(jsonPath("$[*].conferenceUrl", everyItem(is("dummy conference url"))))
			.andExpect(jsonPath("$[*].title", everyItem(is("dummy title"))))
			.andExpect(jsonPath("$[*].startDate", everyItem(new IsoDateFormatMatcher())))
			.andExpect(jsonPath("$[*].endDate", everyItem(new IsoDateFormatMatcher())))
			.andExpect(jsonPath("$[*].summary", everyItem(is("dummy summary"))))
			.andExpect(jsonPath("$[*].country", everyItem(is("dummy country"))))
			.andExpect(jsonPath("$[*].region", everyItem(is("dummy region"))))
			.andExpect(jsonPath("$[*].city", everyItem(is("dummy city"))))
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
			.andExpect(jsonPath("$.title", is("dummy title")))
			.andExpect(jsonPath("$.startDate", new IsoDateFormatMatcher()))
			.andExpect(jsonPath("$.endDate", new IsoDateFormatMatcher()))
			.andExpect(jsonPath("$.summary", is("dummy summary")))
			.andExpect(jsonPath("$.country", is("dummy country")))
			.andExpect(jsonPath("$.region", is("dummy region")))
			.andExpect(jsonPath("$.city", is("dummy city")))
			.andExpect(jsonPath("$.address", is("dummy address")))
			.andExpect(jsonPath("$.logoUrl", is("dummy logo url")))
			.andExpect(jsonPath("$.details", is("dummy details")))
			.andExpect(jsonPath("$.sessions", not(empty())));
	}
}
