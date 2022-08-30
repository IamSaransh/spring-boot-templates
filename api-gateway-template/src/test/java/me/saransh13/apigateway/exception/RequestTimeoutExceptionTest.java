package me.saransh13.apigateway.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestTimeoutExceptionTest {
    /**
     * Method under test: {@link RequestTimeoutException#RequestTimeoutException(String)}
     */
    @Test
    void testConstructor() {
        RequestTimeoutException actualRequestTimeoutException = new RequestTimeoutException("Msg");
        assertNull(actualRequestTimeoutException.getCause());
        assertEquals(0, actualRequestTimeoutException.getSuppressed().length);
        assertEquals("Msg", actualRequestTimeoutException.getMessage());
        assertEquals("Msg", actualRequestTimeoutException.getLocalizedMessage());
    }
}

