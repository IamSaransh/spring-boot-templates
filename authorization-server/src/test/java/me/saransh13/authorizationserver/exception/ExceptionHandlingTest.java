package me.saransh13.authorizationserver.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import me.saransh13.authorizationserver.model.HttpErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ExceptionHandlingTest {
    /**
     * Method under test: {@link ExceptionHandling#accountDisabledException()}
     */
    @Test
    void testAccountDisabledException() {
        ResponseEntity<HttpErrorResponse> actualAccountDisabledExceptionResult = (new ExceptionHandling())
                .accountDisabledException();
        assertTrue(actualAccountDisabledExceptionResult.hasBody());
        assertTrue(actualAccountDisabledExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualAccountDisabledExceptionResult.getStatusCode());
        HttpErrorResponse body = actualAccountDisabledExceptionResult.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getHttpStatus());
        assertEquals(400, body.getStatus());
        assertEquals("YOUR ACCOUNT HAS BEEN DISABLED. IF THIS IS AN ERROR, PLEASE CONTACT ADMINISTRATION",
                body.getMessage());
        assertEquals("BAD REQUEST", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#badCredentialsException()}
     */
    @Test
    void testBadCredentialsException() {
        ResponseEntity<HttpErrorResponse> actualBadCredentialsExceptionResult = (new ExceptionHandling())
                .badCredentialsException();
        assertTrue(actualBadCredentialsExceptionResult.hasBody());
        assertTrue(actualBadCredentialsExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualBadCredentialsExceptionResult.getStatusCode());
        HttpErrorResponse body = actualBadCredentialsExceptionResult.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getHttpStatus());
        assertEquals(400, body.getStatus());
        assertEquals("USERNAME / PASSWORD INCORRECT. PLEASE TRY AGAIN", body.getMessage());
        assertEquals("BAD REQUEST", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#badJwtException()}
     */
    @Test
    void testBadJwtException() {
        ResponseEntity<HttpErrorResponse> actualBadJwtExceptionResult = (new ExceptionHandling()).badJwtException();
        assertTrue(actualBadJwtExceptionResult.hasBody());
        assertTrue(actualBadJwtExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualBadJwtExceptionResult.getStatusCode());
        HttpErrorResponse body = actualBadJwtExceptionResult.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getHttpStatus());
        assertEquals(400, body.getStatus());
        assertEquals("BAD TOKEN PROVODED!", body.getMessage());
        assertEquals("BAD REQUEST", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#accessDeniedException()}
     */
    @Test
    void testAccessDeniedException() {
        ResponseEntity<HttpErrorResponse> actualAccessDeniedExceptionResult = (new ExceptionHandling())
                .accessDeniedException();
        assertTrue(actualAccessDeniedExceptionResult.hasBody());
        assertTrue(actualAccessDeniedExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.FORBIDDEN, actualAccessDeniedExceptionResult.getStatusCode());
        HttpErrorResponse body = actualAccessDeniedExceptionResult.getBody();
        assertEquals(HttpStatus.FORBIDDEN, body.getHttpStatus());
        assertEquals(403, body.getStatus());
        assertEquals("YOU DO NOT HAVE ENOUGH PERMISSION", body.getMessage());
        assertEquals("FORBIDDEN", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#lockedException()}
     */
    @Test
    void testLockedException() {
        ResponseEntity<HttpErrorResponse> actualLockedExceptionResult = (new ExceptionHandling()).lockedException();
        assertTrue(actualLockedExceptionResult.hasBody());
        assertTrue(actualLockedExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.UNAUTHORIZED, actualLockedExceptionResult.getStatusCode());
        HttpErrorResponse body = actualLockedExceptionResult.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED, body.getHttpStatus());
        assertEquals(401, body.getStatus());
        assertEquals("YOUR ACCOUNT HAS BEEN LOCKED. PLEASE CONTACT ADMINISTRATION", body.getMessage());
        assertEquals("UNAUTHORIZED", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#tokenExpiredException(TokenExpiredException)}
     */
    @Test
    void testTokenExpiredException() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        ResponseEntity<HttpErrorResponse> actualTokenExpiredExceptionResult = exceptionHandling.tokenExpiredException(
                new TokenExpiredException("An error occurred", atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        assertTrue(actualTokenExpiredExceptionResult.hasBody());
        assertTrue(actualTokenExpiredExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.UNAUTHORIZED, actualTokenExpiredExceptionResult.getStatusCode());
        HttpErrorResponse body = actualTokenExpiredExceptionResult.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED, body.getHttpStatus());
        assertEquals(401, body.getStatus());
        assertEquals("AN ERROR OCCURRED", body.getMessage());
        assertEquals("UNAUTHORIZED", body.getReason());
    }



    /**
     * Method under test: {@link ExceptionHandling#emailExistException(EmailExistException)}
     */
    @Test
    void testEmailExistException() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        ResponseEntity<HttpErrorResponse> actualEmailExistExceptionResult = exceptionHandling
                .emailExistException(new EmailExistException("An error occurred"));
        assertTrue(actualEmailExistExceptionResult.hasBody());
        assertTrue(actualEmailExistExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualEmailExistExceptionResult.getStatusCode());
        HttpErrorResponse body = actualEmailExistExceptionResult.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getHttpStatus());
        assertEquals(400, body.getStatus());
        assertEquals("AN ERROR OCCURRED", body.getMessage());
        assertEquals("BAD REQUEST", body.getReason());
    }



    /**
     * Method under test: {@link ExceptionHandling#userNotFoundException(UserNotFoundException)}
     */
    @Test
    void testUserNotFoundException() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        ResponseEntity<HttpErrorResponse> actualUserNotFoundExceptionResult = exceptionHandling
                .userNotFoundException(new UserNotFoundException("An error occurred"));
        assertTrue(actualUserNotFoundExceptionResult.hasBody());
        assertTrue(actualUserNotFoundExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserNotFoundExceptionResult.getStatusCode());
        HttpErrorResponse body = actualUserNotFoundExceptionResult.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, body.getHttpStatus());
        assertEquals(400, body.getStatus());
        assertEquals("AN ERROR OCCURRED", body.getMessage());
        assertEquals("BAD REQUEST", body.getReason());
    }





    /**
     * Method under test: {@link ExceptionHandling#methodNotSupportedException(HttpRequestMethodNotSupportedException)}
     */
    @Test
    void testMethodNotSupportedException4() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();

        HashSet<HttpMethod> httpMethodSet = new HashSet<>();
        httpMethodSet.add(HttpMethod.GET);
        HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException = mock(
                HttpRequestMethodNotSupportedException.class);
        when(httpRequestMethodNotSupportedException.getSupportedHttpMethods()).thenReturn(httpMethodSet);
        ResponseEntity<HttpErrorResponse> actualMethodNotSupportedExceptionResult = exceptionHandling
                .methodNotSupportedException(httpRequestMethodNotSupportedException);
        assertTrue(actualMethodNotSupportedExceptionResult.hasBody());
        assertTrue(actualMethodNotSupportedExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualMethodNotSupportedExceptionResult.getStatusCode());
        HttpErrorResponse body = actualMethodNotSupportedExceptionResult.getBody();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, body.getHttpStatus());
        assertEquals(405, body.getStatus());
        assertEquals("THIS REQUEST METHOD IS NOT ALLOWED ON THIS ENDPOINT. PLEASE SEND A 'GET' REQUEST", body.getMessage());
        assertEquals("METHOD NOT ALLOWED", body.getReason());
        verify(httpRequestMethodNotSupportedException).getSupportedHttpMethods();
    }

    /**
     * Method under test: {@link ExceptionHandling#internalServerErrorException(Exception)}
     */
    @Test
    void testInternalServerErrorException() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        ResponseEntity<HttpErrorResponse> actualInternalServerErrorExceptionResult = exceptionHandling
                .internalServerErrorException(new Exception("An error occurred"));
        assertTrue(actualInternalServerErrorExceptionResult.hasBody());
        assertTrue(actualInternalServerErrorExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualInternalServerErrorExceptionResult.getStatusCode());
        HttpErrorResponse body = actualInternalServerErrorExceptionResult.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, body.getHttpStatus());
        assertEquals(500, body.getStatus());
        assertEquals("AN ERROR OCCURRED WHILE PROCESSING THE REQUEST", body.getMessage());
        assertEquals("INTERNAL SERVER ERROR", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#notFoundException(NoResultException)}
     */
    @Test
    void testNotFoundException() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        ResponseEntity<HttpErrorResponse> actualNotFoundExceptionResult = exceptionHandling
                .notFoundException(new NoResultException("An error occurred"));
        assertTrue(actualNotFoundExceptionResult.hasBody());
        assertTrue(actualNotFoundExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualNotFoundExceptionResult.getStatusCode());
        HttpErrorResponse body = actualNotFoundExceptionResult.getBody();
        assertEquals(HttpStatus.NOT_FOUND, body.getHttpStatus());
        assertEquals(404, body.getStatus());
        assertEquals("AN ERROR OCCURRED", body.getMessage());
        assertEquals("NOT FOUND", body.getReason());
    }





    /**
     * Method under test: {@link ExceptionHandling#iOException(IOException)}
     */
    @Test
    void testIOException() {
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        ResponseEntity<HttpErrorResponse> actualIOExceptionResult = exceptionHandling
                .iOException(new IOException("An error occurred"));
        assertTrue(actualIOExceptionResult.hasBody());
        assertTrue(actualIOExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualIOExceptionResult.getStatusCode());
        HttpErrorResponse body = actualIOExceptionResult.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, body.getHttpStatus());
        assertEquals(500, body.getStatus());
        assertEquals("ERROR OCCURRED WHILE PROCESSING FILE", body.getMessage());
        assertEquals("INTERNAL SERVER ERROR", body.getReason());
    }

    /**
     * Method under test: {@link ExceptionHandling#notFound404()}
     */
    @Test
    void testNotFound404() {
        ResponseEntity<HttpErrorResponse> actualNotFound404Result = (new ExceptionHandling()).notFound404();
        assertTrue(actualNotFound404Result.hasBody());
        assertTrue(actualNotFound404Result.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualNotFound404Result.getStatusCode());
        HttpErrorResponse body = actualNotFound404Result.getBody();
        assertEquals(HttpStatus.NOT_FOUND, body.getHttpStatus());
        assertEquals(404, body.getStatus());
        assertEquals("THERE IS NO MAPPING FOR THIS URL", body.getMessage());
        assertEquals("NOT FOUND", body.getReason());
    }
}

