package me.saransh13.authorizationserver.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import me.saransh13.authorizationserver.domain.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static me.saransh13.authorizationserver.constant.SecurityConstant.*;

/**
 * @author saransh
 */
@Component
@Slf4j
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal){
        return JWT.create()
                .withIssuer(ME_SARANSH13)
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
    private JWTVerifier getJwtVerifier(){
        JWTVerifier jwtVerifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            jwtVerifier = JWT.require(algorithm).withIssuer(ME_SARANSH13).build();
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return jwtVerifier;
    }

    public Authentication getAuthentication(String username, HttpServletRequest request){
        UsernamePasswordAuthenticationToken userPasswordAuthToken =
                new UsernamePasswordAuthenticationToken(
                        username,null, null);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String username, String token){
        JWTVerifier verifier = getJwtVerifier();
        return StringUtils.isNoneEmpty(username) && !isTokenExpired(verifier, token);
    }
    public String getSubject(String token){
        JWTVerifier verifier = getJwtVerifier();
        try{return verifier.verify(token).getSubject();} catch (Exception e){
            log.error("error verifying token!");
            throw new JWTVerificationException("Invalid Token");
        }
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

}
