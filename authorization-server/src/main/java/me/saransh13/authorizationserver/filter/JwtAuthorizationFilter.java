package me.saransh13.authorizationserver.filter;

import lombok.extern.slf4j.Slf4j;
import me.saransh13.authorizationserver.constant.SecurityConstant;
import me.saransh13.authorizationserver.util.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static me.saransh13.authorizationserver.constant.SecurityConstant.OPTIONS_HTTP_METHOD;
import static me.saransh13.authorizationserver.constant.SecurityConstant.TOKEN_HEADER_PREFIX;

/**
 * @author saransh
 */
@Component
@Slf4j
public class JwtAuthorizationFilter  extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        }
        else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.TOKEN_HEADER_PREFIX)){
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
            log.info("Token value: {}", token);
            String username = jwtTokenProvider.getSubject(token);
            log.info("username extracted from token is : {} ", username);
            if(jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication()==null){
                Authentication authentication = jwtTokenProvider.getAuthentication(username,request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
