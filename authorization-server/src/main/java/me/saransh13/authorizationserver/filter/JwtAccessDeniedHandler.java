package me.saransh13.authorizationserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.saransh13.authorizationserver.model.HttpErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static me.saransh13.authorizationserver.constant.SecurityConstant.ACCESS_DENIED_MESSAGE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author saransh
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse(UNAUTHORIZED.value(), UNAUTHORIZED,
                UNAUTHORIZED.getReasonPhrase().toUpperCase(), ACCESS_DENIED_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpErrorResponse);
        outputStream.flush();
    }
}
