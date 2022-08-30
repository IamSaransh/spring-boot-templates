package me.saransh13.authorizationserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.saransh13.authorizationserver.domain.Customer;
import me.saransh13.authorizationserver.domain.UserPrincipal;
import me.saransh13.authorizationserver.exception.EmailExistException;
import me.saransh13.authorizationserver.model.LoginHttpRequest;
import me.saransh13.authorizationserver.model.SignupHttpRequest;
import me.saransh13.authorizationserver.service.CustomerService;
import me.saransh13.authorizationserver.util.JWTTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomerAuthController.class})
@ExtendWith(SpringExtension.class)
class CustomerAuthControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerAuthController customerAuthController;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JWTTokenProvider jWTTokenProvider;

    /**
     * Method under test: {@link CustomerAuthController#hello()}
     */
    @Test
    void testHello() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/auth/v1/hello");
        MockMvcBuilders.standaloneSetup(customerAuthController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("hello World!"));
    }

    /**
     * Method under test: {@link CustomerAuthController#register(SignupHttpRequest)}
     */
    @Test
    void testRegister() throws Exception {
        Customer customer = new Customer();
        customer.setContactNumber("42");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");
        when(customerService.register((String) any(), (String) any(), (String) any(), (String) any(), (String) any()))
                .thenReturn(customer);

        SignupHttpRequest signupHttpRequest = new SignupHttpRequest();
        signupHttpRequest.setContactNumber("https://example.org/example");
        signupHttpRequest.setEmail("https://example.org/example");
        signupHttpRequest.setFirstName("Jane");
        signupHttpRequest.setLastName("Doe");
        signupHttpRequest.setPassword("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(signupHttpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerAuthController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"contactNumber\":\"42\",\"password\":"
                                        + "\"iloveyou\",\"pfpUrl\":\"https://example.org/example\",\"enabled\":true}"));
    }

    /**
     * Method under test: {@link CustomerAuthController#register(SignupHttpRequest)}
     */
    @Test
    void testRegister2() throws Exception {
        when(customerService.register((String) any(), (String) any(), (String) any(), (String) any(), (String) any()))
                .thenThrow(new EmailExistException("An error occurred"));

        SignupHttpRequest signupHttpRequest = new SignupHttpRequest();
        signupHttpRequest.setContactNumber("https://example.org/example");
        signupHttpRequest.setEmail("https://example.org/example");
        signupHttpRequest.setFirstName("Jane");
        signupHttpRequest.setLastName("Doe");
        signupHttpRequest.setPassword("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(signupHttpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerAuthController)
                .build()
                .perform(requestBuilder);
        Date data = new Date();
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
   }

    /**
     * Method under test: {@link CustomerAuthController#hello()}
     */
    @Test
    void testHello2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/auth/v1/hello", "Uri Vars");
        MockMvcBuilders.standaloneSetup(customerAuthController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("hello World!"));
    }


    /**
     * Method under test: {@link CustomerAuthController#login(LoginHttpRequest)}
     */
    @Test
    void testLogin() throws Exception {
        Customer customer = new Customer();
        customer.setContactNumber("42");
        customer.setEmail("jane.doe@example.org");
        customer.setEnabled(true);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setPassword("iloveyou");
        customer.setPfpUrl("https://example.org/example");
        when(customerService.getCustomerByEmail((String) any())).thenReturn(customer);
        when(jWTTokenProvider.generateJwtToken((UserPrincipal) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginHttpRequest loginHttpRequest = new LoginHttpRequest();
        loginHttpRequest.setPassword("https://example.org/example");
        loginHttpRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginHttpRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerAuthController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"contactNumber\":\"42\",\"password\":"
                                        + "\"iloveyou\",\"pfpUrl\":\"https://example.org/example\",\"enabled\":true}"));
    }
}

