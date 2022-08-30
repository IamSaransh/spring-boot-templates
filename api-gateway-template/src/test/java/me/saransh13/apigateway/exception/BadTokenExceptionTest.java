package me.saransh13.apigateway.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BadTokenExceptionTest {
    /**
     * Method under test: {@link BadTokenException#BadTokenException(String)}
     */
    @Test
    void testConstructor() {
        BadTokenException actualBadTokenException = new BadTokenException("Msg");
        assertNull(actualBadTokenException.getCause());
        assertEquals(0, actualBadTokenException.getSuppressed().length);
        assertEquals("Msg", actualBadTokenException.getMessage());
        assertEquals("Msg", actualBadTokenException.getLocalizedMessage());
    }
}

