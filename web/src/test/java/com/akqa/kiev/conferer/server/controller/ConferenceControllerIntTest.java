package com.akqa.kiev.conferer.server.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.akqa.kiev.conferer.server.matchers.IsoDateFormatMatcher;

public class ConferenceControllerIntTest extends AbstractControllerIntTest {
    


	@Test
	public void conferences_existingConferences() throws Exception {
		preCheckJsonResponse("/conferences?year=2013&month=1")
			.andExpect(jsonPath("$[*]", hasSize(3)))
			.andExpect(jsonPath("$[*].id", contains(1, 2, 3)))
			.andExpect(jsonPath("$[*].conferenceUrl", everyItem(is("dummy conference url"))))
			.andExpect(jsonPath("$[*].title", everyItem(not(isEmptyOrNullString()))))
			.andExpect(jsonPath("$[*].startDate", contains("02-01-2013T00:00:00.000+0900", "04-01-2013T00:00:00.000+0800", "14-01-2013T00:00:00.000+0200")))
			.andExpect(jsonPath("$[*].endDate", contains("05-01-2013T00:00:00.000+0900", "10-01-2013T00:00:00.000+0800", "20-01-2013T00:00:00.000+0200")))
			.andExpect(jsonPath("$[*].summary", everyItem(not(isEmptyOrNullString()))))
			.andExpect(jsonPath("$[*].country", contains("Japan", "China", "Ukraine")))
			.andExpect(jsonPath("$[*].region", contains("Kanto", "North China", null)))
			.andExpect(jsonPath("$[*].city", contains("Tokyo", "Beijing", "Kiev")))
			.andExpect(jsonPath("$[*].address", everyItem(is("dummy address"))))
			.andExpect(jsonPath("$[*].logoUrl", contains("https://qconnewyork.com/sites/all/themes/qcon_master/images/logo.jpg",
                    "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRLiqx5l8ys8sQu-gcXfCcUCQivPwKjBIRFXf0WKYGtsKaAG6KTg5oPbjE6",
                    "https://qconnewyork.com/sites/all/themes/qcon_master/images/logo.jpg")))
			.andExpect(jsonPath("$[*].details").doesNotExist())
			.andExpect(jsonPath("$[*].sessions").doesNotExist());
	}
	
	@Test
	public void conferences_noArgs() throws Exception {
		preCheckJsonResponse("/conferences");
	}
	
	@Test
	public void conferences_nonexistingConferences() throws Exception {
		preCheckJsonResponse("/conferences?year=2012&month=1")
			.andExpect(jsonPath("$[*]", empty()));
	}
	
	@Test
	public void conferences_yearOnly() throws Exception {
		mockMvc.perform(get("/conferences?year=2012").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void conferences_monthOnly() throws Exception {
		mockMvc.perform(get("/conferences?month=1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void conferences_outOfRangeParameters() throws Exception {
		String[] samples = {
			"/conferences?month=0&year=2012",
			"/conferences?month=13&year=2012",
			"/conferences?month=1&year=1800",
			"/conferences?month=1&year=2300",
			"/conferences?month=-1&year=-2012"
		};
		
		for (String url : samples) {
			mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		}
	}
	
	@Test
	public void conference_existingConference() throws Exception {
		preCheckJsonResponse("/conferences/1")
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.conferenceUrl", is("dummy conference url")))
			.andExpect(jsonPath("$.title", is("QCon New York 2013")))
			.andExpect(jsonPath("$.startDate", new IsoDateFormatMatcher()))
			.andExpect(jsonPath("$.endDate", new IsoDateFormatMatcher()))
			.andExpect(jsonPath("$.summary", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.country", is("Japan")))
			.andExpect(jsonPath("$.region", is("Kanto")))
			.andExpect(jsonPath("$.city", is("Tokyo")))
			.andExpect(jsonPath("$.address", is("dummy address")))
			.andExpect(jsonPath("$.logoUrl", is("https://qconnewyork.com/sites/all/themes/qcon_master/images/logo.jpg")))
			.andExpect(jsonPath("$.details", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.sessions", not(empty())));
	}

	@Test
	public void conference_nonExistingConference() throws Exception {
		mockMvc.perform(get("/conferences/-1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
    @Test
    public void conference_allConferences() throws Exception {
        mockMvc.perform(get("/conferences/all").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
	
	@Test
	public void conference_badConferenceId() throws Exception {
		mockMvc.perform(get("/conferences/wrong & id").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void conference_months() throws Exception {
		preCheckJsonResponse("/conferences/months")
			.andExpect(jsonPath("$[*]", contains(
				1356998400000L, // Jan 2013
				1362096000000L, // Mar 2013
				1364774400000L  // Apr 2013
			)));
	}
	
	@Test
	public void deleteExistingConference() throws Exception{
        mockMvc.perform(get("/delete/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
}
