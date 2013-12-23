
package com.akqa.kiev.conferer.server.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

public class GcmControllerIntTest extends AbstractControllerIntTest{
   

    @Test
    public void testGcmRegistration() throws Exception {
        mockMvc.perform(get("/gcm//register/ABCD")).andExpect(status().isOk());
    }
    
    @Test
    public void testGcmUnregister() throws Exception {
        mockMvc.perform(get("/gcm/unregister/ABCD")).andExpect(status().isOk());
    }
}
