package me.saransh13.authorizationserver.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmailExistExceptionTest {
    /**
     * Method under test: {@link EmailExistException#EmailExistException(String)}
     */
    @Test
    void testConstructor() {
        EmailExistException actualEmailExistException = new EmailExistException("An error occurred");
        assertNull(actualEmailExistException.getCause());
        assertEquals(0, actualEmailExistException.getSuppressed().length);
        assertEquals("An error occurred", actualEmailExistException.getMessage());
        assertEquals("An error occurred", actualEmailExistException.getLocalizedMessage());
    }
}

