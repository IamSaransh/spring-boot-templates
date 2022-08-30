package me.saransh13.authorizationserver.exception;

/**
 * @author saransh
 */
public class UserNotFoundException extends  Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
