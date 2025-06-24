package com.medilabo.frontapplication.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenInterceptorTest {

    private TokenInterceptor tokenInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenInterceptor = new TokenInterceptor();
    }

    // Login Page access, no Token verification for this.
    @Test
    void testPreHandle_LoginPage() throws Exception {
        // Arrange
        when(request.getServletPath()).thenReturn("/loginPage");

        // Act
        boolean result = tokenInterceptor.preHandle(request, response, new Object());

        // Assert
        assertTrue(result);
        verify(response, never()).sendRedirect(anyString());
    }

    // Token is missing, so a 'Login Page' redirection is triggered
    @Test
    void TestPreHandle_MissingToken() throws Exception {
        // Arrange
        when(request.getServletPath()).thenReturn("/someOtherPage");
        when(request.getSession()).thenReturn(mock(jakarta.servlet.http.HttpSession.class));
        when(request.getSession().getAttribute("token")).thenReturn(null);

        // Act
        boolean result = tokenInterceptor.preHandle(request, response, new Object());

        // Assert
        assertFalse(result);
        verify(response).sendRedirect("/loginPage");
    }

    // No login page redirection since a Token is present
    @Test
    void TestPreHandle_PresentToken() throws Exception {
        // Arrange
        when(request.getServletPath()).thenReturn("/someOtherPage");
        when(request.getSession()).thenReturn(mock(jakarta.servlet.http.HttpSession.class));
        when(request.getSession().getAttribute("token")).thenReturn("validToken");

        // Act
        boolean result = tokenInterceptor.preHandle(request, response, new Object());

        // Assert
        assertTrue(result);
        verify(response, never()).sendRedirect(anyString());
    }
}
