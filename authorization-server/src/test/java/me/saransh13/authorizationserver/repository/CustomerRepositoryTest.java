package me.saransh13.authorizationserver.repository;

import me.saransh13.authorizationserver.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

@ContextConfiguration(classes = {CustomerRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"me.saransh13.authorizationserver.domain"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Method under test: {@link CustomerRepository#findCustomerByEmail(String)}
     */
    @Test
    @DirtiesContext
    @Transactional
    void testFindCustomerByEmail() {

        Customer customer = new Customer();
        customer.setContactNumber("8003339977");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");

        customerRepository.save(customer);
        customerRepository.flush();
        Customer fetched = customerRepository.findCustomerByEmail("jane.doe@example.org");

        Assertions.assertTrue(fetched.getEmail().equalsIgnoreCase("jane.doe@example.org"));
        Assertions.assertTrue(fetched.getFirstName().equalsIgnoreCase("Jane"));

    }
}

