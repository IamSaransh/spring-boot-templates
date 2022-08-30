package me.saransh13.authorizationserver.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupHttpRequest {
    @Email(message = "Invalid email provided")
    private String email;
    @Min(value=6)
    private String password;
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @Pattern(
            regexp = "^.\\d{10}$",
            message = "Contact number should be 10 character long " +
                    "and should only consist of digits"
    )
    private String contactNumber;
}
