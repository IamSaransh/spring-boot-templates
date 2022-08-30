package me.saransh13.authorizationserver.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.io.Serializable;
/**
 * @author saransh
 */

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements  Serializable {

    @Id @NonNull @Email(message = "Please provide a valid email address")
    @Column(updatable = false, nullable = false)
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull @Length(min = 10 , max = 10)
    private String contactNumber;
    @NonNull
    private String password;
    private String pfpUrl;
    private boolean isEnabled;



}
