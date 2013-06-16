package com.akqa.kiev.conferer.server.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

public class SpeakerControllerTest extends AbstractControllerTest {

	@Test
	public void speaker_existing() throws Exception {
		preCheckJsonResponse("/speakers/2")
			.andExpect(jsonPath("$.id", is(2)))
			.andExpect(jsonPath("$.speakerUrl", is("some url")))
			.andExpect(jsonPath("$.firstName", is("Alain")))
			.andExpect(jsonPath("$.lastName", is("Hélaïli")))
			.andExpect(jsonPath("$.photoUrl", is("some photo url")))
			.andExpect(jsonPath("$.about", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.socialLinks.facebook", is("facebook link")))
			.andExpect(jsonPath("$.socialLinks.twitter", is("@tcoupland")))
			.andExpect(jsonPath("$.socialLinks.linkedin", is("linkedin link")));
	}
	
	@Test
	public void speaker_nonExisting() throws Exception {
		mockMvc.perform(get("/speakers/-1").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void speaker_wrongId() throws Exception {
		mockMvc.perform(get("/speakers/- &").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
}
