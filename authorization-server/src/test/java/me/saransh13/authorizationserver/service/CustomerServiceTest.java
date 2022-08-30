package me.saransh13.authorizationserver.service;

import me.saransh13.authorizationserver.domain.Customer;
import me.saransh13.authorizationserver.exception.EmailExistException;
import me.saransh13.authorizationserver.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        Customer customer = new Customer();
        customer.setContactNumber("42");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");
        when(customerRepository.findCustomerByEmail((String) any())).thenReturn(customer);
        assertTrue(customerService.loadUserByUsername("janedoe").isEnabled());
        verify(customerRepository).findCustomerByEmail((String) any());
    }


    /**
     * Method under test: {@link CustomerService#register(String, String, String, String, String)}
     */
    @Test
    void testRegister() throws EmailExistException {
        Customer customer = new Customer();
        customer.setContactNumber("42");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");
        when(customerRepository.findCustomerByEmail((String) any())).thenReturn(customer);
        assertThrows(EmailExistException.class,
                () -> customerService.register("jane.doe@example.org", "Jane", "Doe", "42", "iloveyou"));
        verify(customerRepository).findCustomerByEmail((String) any());
    }


    @Test
    void testRegister_exceptionEmailExists() throws EmailExistException {
        when(customerRepository.findCustomerByEmail((String) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class,
                () -> customerService.register("jane.doe@example.org", "Jane", "Doe", "42", "iloveyou"));
        verify(customerRepository).findCustomerByEmail((String) any());
    }



    /**
     * Method under test: {@link CustomerService#getCustomerByEmail(String)}
     */
    @Test
    void testGetCustomerByEmail() {
        Customer customer = new Customer();
        customer.setContactNumber("42");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");
        when(customerRepository.findCustomerByEmail((String) any())).thenReturn(customer);
        assertSame(customer, customerService.getCustomerByEmail("jane.doe@example.org"));
        verify(customerRepository).findCustomerByEmail((String) any());
    }

}

