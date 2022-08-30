package me.saransh13.authorizationserver.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JWTTokenProvider.class})
@ExtendWith(SpringExtension.class)
class JWTTokenProviderTest {
    @Autowired
    private JWTTokenProvider jWTTokenProvider;




    /**
     * Method under test: {@link JWTTokenProvider#getAuthentication(String, HttpServletRequest)}
     */
    @Test
    void testGetAuthentication() {
        Authentication actualAuthentication = jWTTokenProvider.getAuthentication("janedoe", new MockHttpServletRequest());
        assertTrue(actualAuthentication.getAuthorities().isEmpty());
        assertTrue(actualAuthentication.isAuthenticated());
        assertEquals("janedoe", actualAuthentication.getPrincipal());
        Object details = actualAuthentication.getDetails();
        assertTrue(details instanceof WebAuthenticationDetails);
        assertNull(actualAuthentication.getCredentials());
        assertNull(((WebAuthenticationDetails) details).getSessionId());
    }



    /**
     * Method under test: {@link JWTTokenProvider#getAuthentication(String, HttpServletRequest)}
     */
    @Test
    void testGetAuthentication3() {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getRemoteAddr()).thenReturn("42 Main St");
        when(defaultMultipartHttpServletRequest.getSession(anyBoolean())).thenReturn(new MockHttpSession());
        Authentication actualAuthentication = jWTTokenProvider.getAuthentication("janedoe",
                defaultMultipartHttpServletRequest);
        assertTrue(actualAuthentication.getAuthorities().isEmpty());
        assertTrue(actualAuthentication.isAuthenticated());
        assertEquals("janedoe", actualAuthentication.getPrincipal());
        Object details = actualAuthentication.getDetails();
        assertTrue(details instanceof WebAuthenticationDetails);
        assertNull(actualAuthentication.getCredentials());
        assertEquals("42 Main St", ((WebAuthenticationDetails) details).getRemoteAddress());
        verify(defaultMultipartHttpServletRequest).getRemoteAddr();
        verify(defaultMultipartHttpServletRequest).getSession(anyBoolean());
    }


    /**
     * Method under test: {@link JWTTokenProvider#isTokenValid(String, String)}
     */
    @Test
    void testIsTokenValid2() {
        assertFalse(jWTTokenProvider.isTokenValid("", "ABC123"));
    }

    /**
     * Method under test: {@link JWTTokenProvider#getSubject(String)}
     */
    @Test
    void testGetSubject() {
        assertThrows(JWTVerificationException.class, () -> jWTTokenProvider.getSubject("ABC123"));
        assertThrows(JWTVerificationException.class, () -> jWTTokenProvider.getSubject("Saransh13.meSaransh13.me"));
        assertThrows(JWTVerificationException.class, () -> jWTTokenProvider.getSubject("ABC123Saransh13.meSaransh13.me"));
    }
}

