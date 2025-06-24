package com.medilabo.authenticationapplication.service;

import com.medilabo.authenticationapplication.security.JwtProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private JwtProcessor jwtProcessor;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // should successfully generate a token based on username
    @Test
    void testGenerateToken() {
        // Arrange
        String username = "testUser";
        String expectedToken = "expectedToken";
        when(jwtProcessor.generateToken(username)).thenReturn(expectedToken);

        // Act
        String result = authenticationService.generateToken(username);

        // Assert
        assertEquals("expectedToken", result);
        verify(jwtProcessor, times(1)).generateToken(username);
    }

    // should return 'true' as the Token would be valid
    @Test
    void testValidateToken_Success() {
        String token = "testToken";

        when(jwtProcessor.validateToken(token)).thenReturn(true);

        boolean result = authenticationService.validateToken(token);

        assertTrue(result);
        verify(jwtProcessor, times(1)).validateToken(token);
    }

    // should return 'false' as the Token would be invalid
    @Test
    void testValidateToken_Invalid() {
        String token = "invalidToken";

        when(jwtProcessor.validateToken(token)).thenReturn(false);

        boolean result = authenticationService.validateToken(token);

        assertFalse(result);
        verify(jwtProcessor, times(1)).validateToken(token);
    }
}
