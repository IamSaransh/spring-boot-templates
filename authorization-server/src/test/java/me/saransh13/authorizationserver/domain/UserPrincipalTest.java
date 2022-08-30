package me.saransh13.authorizationserver.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserPrincipalTest {
    /**
     * Method under test: {@link UserPrincipal#UserPrincipal(Customer)}
     */
    @Test
    void testConstructor() {
        Customer customer = new Customer();
        customer.setContactNumber("42");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");
        UserPrincipal actualUserPrincipal = new UserPrincipal(customer);
        assertTrue(actualUserPrincipal.getAuthorities().isEmpty());
        assertTrue(actualUserPrincipal.isEnabled());
        assertTrue(actualUserPrincipal.isCredentialsNonExpired());
        assertTrue(actualUserPrincipal.isAccountNonLocked());
        assertTrue(actualUserPrincipal.isAccountNonExpired());
        assertEquals("jane.doe@example.org", actualUserPrincipal.getUsername());
        assertEquals("iloveyou", actualUserPrincipal.getPassword());
    }
}

