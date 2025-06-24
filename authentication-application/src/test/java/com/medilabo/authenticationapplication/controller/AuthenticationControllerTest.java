package com.medilabo.authenticationapplication.controller;

import com.medilabo.authenticationapplication.model.AuthenticationRequest;
import com.medilabo.authenticationapplication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // should successfully return a newly generated Token
    @Test
    void testGenerateToken_Success() {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("password");
        String token = "generatedToken";
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationService.generateToken(authRequest.getUsername())).thenReturn(token);

        ResponseEntity<String> response = authenticationController.generateToken(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
    }

    // should return 'Invalid Access' exception because credentials are incorrect
    @Test
    void testGenerateToken_Failure() {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("password");
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationController.generateToken(authRequest);
        });
        assertEquals("User invalid access ", exception.getMessage());
    }

    // Should return 'true' as the client sent Valid Token
    @Test
    void testValidateToken_Success() {
        String token = "validToken";
        when(authenticationService.validateToken(token)).thenReturn(true);

        ResponseEntity<Boolean> response = authenticationController.validateToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    // should return an 'InvalidToken' String as the client sent an invalid Token
    @Test
    void testValidateToken_Failure() {
        String token = "invalidToken";
        when(authenticationService.validateToken(token)).thenThrow(new RuntimeException("Token invalid"));

        ResponseEntity<Boolean> response = authenticationController.validateToken(token);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(false, response.getBody());
    }
}
