package me.saransh13.authorizationserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginHttpRequest {
    @Email(message = "Invalid email provided")
    private String username;
    @Min(value=6)
    private String password;
}
