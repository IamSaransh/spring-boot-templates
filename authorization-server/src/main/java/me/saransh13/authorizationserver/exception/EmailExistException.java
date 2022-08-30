package me.saransh13.authorizationserver.exception;

/**
 * @author saransh
 */
public class EmailExistException extends  Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
