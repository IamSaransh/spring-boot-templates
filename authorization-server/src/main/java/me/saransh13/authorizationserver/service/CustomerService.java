package me.saransh13.authorizationserver.service;

import lombok.extern.slf4j.Slf4j;
import me.saransh13.authorizationserver.domain.Customer;
import me.saransh13.authorizationserver.domain.UserPrincipal;
import me.saransh13.authorizationserver.exception.EmailExistException;
import me.saransh13.authorizationserver.model.AuthenticateResponse;
import me.saransh13.authorizationserver.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;

/**
 * @author saransh
 */
@Slf4j
@Service
@Transactional
@Qualifier("userDetailsService")
public class CustomerService implements UserDetailsService {
    private BCryptPasswordEncoder passwordEncoder;
    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer =  repository.findCustomerByEmail(username);
        if(customer == null){
            log.error("User not found by username {}",  username);
            throw new UsernameNotFoundException("User not found by username" +  username);
        }
        else{
            UserPrincipal userPrincipal = new UserPrincipal(customer);
            log.info("Returning found user by username {}" , username);
            log.info("The returned user has password as : {}", userPrincipal.getPassword());
            return userPrincipal;
        }
    }

    public Customer register( String email, String firstname, String lastname, String contactNumber, String password) throws EmailExistException {
        validateEmailForRegistration(email);
        Customer customer = repository.save(
                Customer.builder().
                        firstName(firstname)
                        .lastName(lastname)
                        .password(encodePassword(password))
                        .email(email)
                        .contactNumber(contactNumber)
                        .pfpUrl(generateRandomPfpUrl())
                        .isEnabled(true)
                        .build()
        );
        log.info("Customer " + customer + " saved");
        return customer;
    }

    private String generateRandomPfpUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/customer/image/profile/temp").toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateEmailForRegistration(String email) throws EmailExistException {
        if(StringUtils.isNoneBlank(email)){
            Customer customer = getCustomerByEmail(email);
            if(customer!=null){
                throw new EmailExistException("User with email " + email + " already exists!");
            }
        }
    }

    public Customer getCustomerByEmail(String email){
        return repository.findCustomerByEmail(email);
    }

    public AuthenticateResponse authenticateCustomer() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error(principal.getClass().toString());
        log.error(principal.toString());
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            log.info("I'm instance of userDetai;s");
        } else {
            username = principal.toString();
            log.info("else block!!!!");
        }

        var customer = repository.findById(username)
                .orElseThrow(() -> {
                    var errorMsg = String.format("CustomerService.getCustomer () : " +
                            "Customer not found with username %s", username);
                    log.error(errorMsg);
                    return new UsernameNotFoundException(errorMsg);
                });

        return AuthenticateResponse.builder()
                .isAuthenticated(true)
                .httpStatus(HttpStatus.OK)
                .message("You have authenticated Successfully")
                .email(customer.getEmail())
                .build();
    }

}
