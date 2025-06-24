package com.medilabo.frontapplication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
class HomeControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession getSessionWithToken() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("token", "mock-token"); // Mock token to pass the TokenInterceptor checkpoint
        return session;
    }

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/home").session(getSessionWithToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("home")); // Expect the view name to be "home"
    }
}

