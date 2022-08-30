package me.saransh13.authorizationserver.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import me.saransh13.authorizationserver.model.HttpErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

/**
 * @author saransh
 */
@RestControllerAdvice
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandling implements ErrorController {
    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpErrorResponse>  accountDisabledException(){
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpErrorResponse> badCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<HttpErrorResponse> badJwtException() {
        return createHttpResponse(BAD_REQUEST, "BAD TOKEN PROVODED!");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpErrorResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpErrorResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpErrorResponse> tokenExpiredException(TokenExpiredException exception) {
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpErrorResponse> emailExistException(EmailExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> userNotFoundException(UserNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpErrorResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> internalServerErrorException(Exception exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpErrorResponse> notFoundException(NoResultException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpErrorResponse> iOException(IOException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    private ResponseEntity<HttpErrorResponse> createHttpResponse (HttpStatus httpStatus, String message) {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse(httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase());
        return new ResponseEntity<>(httpErrorResponse, httpStatus);
    }

    @RequestMapping("/error")
    public ResponseEntity<HttpErrorResponse> notFound404() {
        log.warn("Page not found! 404");
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }
}
