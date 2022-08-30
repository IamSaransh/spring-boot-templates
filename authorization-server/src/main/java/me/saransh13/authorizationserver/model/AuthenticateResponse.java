package me.saransh13.authorizationserver.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticateResponse {

    private boolean isAuthenticated;
    private HttpStatus httpStatus;
    private  String message;
    private String email;

}
