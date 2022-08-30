package me.saransh13.authorizationserver.constant;

/**
 * @author saransh
 */
public class SecurityConstant {


    private SecurityConstant(){}
    public static final long EXPIRATION_TIME = (long) 1.8e+6;
    public static final String TOKEN_HEADER_PREFIX  =  "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String FORBIDDEN_MESSAGE = "You are forbidden to access this value";
    public static final String ACCESS_DENIED_MESSAGE = "You are not Accessed to access this value";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token Cannot Be Verified";
    public static final String ME_SARANSH13 = "Saransh13.me";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {
            "/auth/v1/login",
            "/auth/v1/signup",
            "/actuator/**",
            "/swagger-ui/**",
            "/configuration/**",
            "/h2-console/**"
    };

}
