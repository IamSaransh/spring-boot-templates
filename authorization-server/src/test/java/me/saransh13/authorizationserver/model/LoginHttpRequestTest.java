package me.saransh13.authorizationserver.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginHttpRequestTest {

    @Test
    void testCanEqual2() {
        LoginHttpRequest loginHttpRequest = new LoginHttpRequest("janedoe", "https://example.org/example");
        assertTrue(loginHttpRequest.canEqual(new LoginHttpRequest("janedoe", "https://example.org/example")));
    }


    @Test
    void testConstructor() {
        LoginHttpRequest actualLoginHttpRequest = new LoginHttpRequest();
        actualLoginHttpRequest.setPassword("https://example.org/example");
        actualLoginHttpRequest.setUsername("janedoe");
        String actualToStringResult = actualLoginHttpRequest.toString();
        assertEquals("https://example.org/example", actualLoginHttpRequest.getPassword());
        assertEquals("janedoe", actualLoginHttpRequest.getUsername());
        assertEquals("LoginHttpRequest(username=janedoe, password=https://example.org/example)", actualToStringResult);
    }


    @Test
    void testConstructor2() {
        LoginHttpRequest actualLoginHttpRequest = new LoginHttpRequest("janedoe", "https://example.org/example");
        actualLoginHttpRequest.setPassword("https://example.org/example");
        actualLoginHttpRequest.setUsername("janedoe");
        String actualToStringResult = actualLoginHttpRequest.toString();
        assertEquals("https://example.org/example", actualLoginHttpRequest.getPassword());
        assertEquals("janedoe", actualLoginHttpRequest.getUsername());
        assertEquals("LoginHttpRequest(username=janedoe, password=https://example.org/example)", actualToStringResult);
    }

}

