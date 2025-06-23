package com.medilabo.frontapplication.controller;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class TestTokenUtils {
    public static MockHttpServletRequestBuilder withToken(MockHttpServletRequestBuilder requestBuilder) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("token", "mock-token"); // Add a mock token to pass MVC token validation
        return requestBuilder.session(session);
    }
}
