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
			.andExpect(jsonPath("$.speakerUrl", is("http://johnsmith-johnsimages.blogspot.com/2013/08/montreal-street-fashion-festival-2013.html")))
			.andExpect(jsonPath("$.firstName", is("Alain")))
			// FIXME: Fix Unicode issue
			// .andExpect(jsonPath("$.lastName", is("Hélaïli")))
			.andExpect(jsonPath("$.photoUrl", is("http://www.bicompetenceforum.com/wp-content/uploads/2013/04/fabriziodepetris.jpg")))
			.andExpect(jsonPath("$.about", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.socialLinks.facebook", is("http://www.facebook.com/acousticsmith")))
			.andExpect(jsonPath("$.socialLinks.twitter", is("http://twitter.com/JohnWSmith")))
			.andExpect(jsonPath("$.socialLinks.linkedin", is("http://uk.linkedin.com/in/johnwsmithchange")));
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
	
    @Test
    public void speaker_sessions() throws Exception {
        mockMvc.perform(get("/speakers/1/sessions").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
