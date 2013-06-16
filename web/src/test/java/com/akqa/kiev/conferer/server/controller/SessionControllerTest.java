package com.akqa.kiev.conferer.server.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

public class SessionControllerTest extends AbstractControllerTest {

	@Test
	public void session_existing() throws Exception {
		preCheckJsonResponse("/sessions/1")
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.title", is("Avoiding Invisible Impediments to High Performance")))
			.andExpect(jsonPath("$.summary", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.startTime", is("02-01-2013T09:00:00.000+0900")))
			.andExpect(jsonPath("$.endTime", is("02-01-2013T11:00:00.000+0900")))
			.andExpect(jsonPath("$.type", is("Workshop")));
	}
	
	@Test
	public void session_nonExisting() throws Exception {
		mockMvc.perform(get("/sessions/-1").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void session_wrongId() throws Exception {
		mockMvc.perform(get("/sessions/- &").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
}
