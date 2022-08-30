package me.saransh13.authorizationserver.filter;

import me.saransh13.authorizationserver.util.JWTTokenProvider;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JwtAuthorizationFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class JwtAuthorizationFilterTest {
    @MockBean
    private JWTTokenProvider jWTTokenProvider;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal() throws IOException, ServletException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(mockHttpServletRequest, response, filterChain);
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        assertFalse(mockHttpServletRequest.isRequestedSessionIdFromURL());
        assertTrue(mockHttpServletRequest.isRequestedSessionIdFromCookie());
        assertFalse(mockHttpServletRequest.isAsyncSupported());
        assertFalse(mockHttpServletRequest.isAsyncStarted());
        assertTrue(mockHttpServletRequest.isActive());
        assertTrue(mockHttpServletRequest.getSession() instanceof MockHttpSession);
        assertEquals("", mockHttpServletRequest.getServletPath());
        assertEquals(80, mockHttpServletRequest.getServerPort());
        assertEquals("localhost", mockHttpServletRequest.getServerName());
        assertEquals("http", mockHttpServletRequest.getScheme());
        assertEquals("", mockHttpServletRequest.getRequestURI());
        assertEquals(80, mockHttpServletRequest.getRemotePort());
        assertEquals("localhost", mockHttpServletRequest.getRemoteHost());
        assertEquals("HTTP/1.1", mockHttpServletRequest.getProtocol());
        assertEquals("", mockHttpServletRequest.getMethod());
        assertEquals(80, mockHttpServletRequest.getLocalPort());
        assertEquals("localhost", mockHttpServletRequest.getLocalName());
        assertTrue(mockHttpServletRequest.getInputStream() instanceof DelegatingServletInputStream);
        assertEquals(DispatcherType.REQUEST, mockHttpServletRequest.getDispatcherType());
        assertEquals("", mockHttpServletRequest.getContextPath());
        assertEquals(-1L, mockHttpServletRequest.getContentLengthLong());
    }



    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal3() throws IOException, ServletException {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("https://example.org/example");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }

    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal4() throws IOException, ServletException {
        when(jWTTokenProvider.getAuthentication((String) any(), (HttpServletRequest) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(jWTTokenProvider.isTokenValid((String) any(), (String) any())).thenReturn(true);
        when(jWTTokenProvider.getSubject((String) any())).thenReturn("Hello from the Dreaming Spires");
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(jWTTokenProvider).isTokenValid((String) any(), (String) any());
        verify(jWTTokenProvider).getSubject((String) any());
        verify(jWTTokenProvider).getAuthentication((String) any(), (HttpServletRequest) any());
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }

    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal5() throws IOException, ServletException {
        when(jWTTokenProvider.getAuthentication((String) any(), (HttpServletRequest) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(jWTTokenProvider.isTokenValid((String) any(), (String) any())).thenReturn(false);
        when(jWTTokenProvider.getSubject((String) any())).thenReturn("Hello from the Dreaming Spires");
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(jWTTokenProvider).isTokenValid((String) any(), (String) any());
        verify(jWTTokenProvider).getSubject((String) any());
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }



    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal8() throws IOException, ServletException {
        when(jWTTokenProvider.getAuthentication((String) any(), (HttpServletRequest) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(jWTTokenProvider.isTokenValid((String) any(), (String) any())).thenReturn(true);
        when(jWTTokenProvider.getSubject((String) any())).thenReturn("Hello from the Dreaming Spires");
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("OPTIONS");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, mockHttpServletResponse, filterChain);
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        assertEquals(200, mockHttpServletResponse.getStatus());
    }

    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal9() throws IOException, ServletException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(mockHttpServletRequest, response, filterChain);
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        assertFalse(mockHttpServletRequest.isRequestedSessionIdFromURL());
        assertTrue(mockHttpServletRequest.isRequestedSessionIdFromCookie());
        assertFalse(mockHttpServletRequest.isAsyncSupported());
        assertFalse(mockHttpServletRequest.isAsyncStarted());
        assertTrue(mockHttpServletRequest.isActive());
        assertTrue(mockHttpServletRequest.getSession() instanceof MockHttpSession);
        assertEquals("", mockHttpServletRequest.getServletPath());
        assertEquals(80, mockHttpServletRequest.getServerPort());
        assertEquals("localhost", mockHttpServletRequest.getServerName());
        assertEquals("http", mockHttpServletRequest.getScheme());
        assertEquals("", mockHttpServletRequest.getRequestURI());
        assertEquals(80, mockHttpServletRequest.getRemotePort());
        assertEquals("localhost", mockHttpServletRequest.getRemoteHost());
        assertEquals("HTTP/1.1", mockHttpServletRequest.getProtocol());
        assertEquals("", mockHttpServletRequest.getMethod());
        assertEquals(80, mockHttpServletRequest.getLocalPort());
        assertEquals("localhost", mockHttpServletRequest.getLocalName());
        assertTrue(mockHttpServletRequest.getInputStream() instanceof DelegatingServletInputStream);
        assertEquals(DispatcherType.REQUEST, mockHttpServletRequest.getDispatcherType());
        assertEquals("", mockHttpServletRequest.getContextPath());
        assertEquals(-1L, mockHttpServletRequest.getContentLengthLong());
    }



    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal11() throws IOException, ServletException {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("https://example.org/example");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }

    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal12() throws IOException, ServletException {
        when(jWTTokenProvider.getAuthentication((String) any(), (HttpServletRequest) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(jWTTokenProvider.isTokenValid((String) any(), (String) any())).thenReturn(true);
        when(jWTTokenProvider.getSubject((String) any())).thenReturn("Hello from the Dreaming Spires");
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(jWTTokenProvider).isTokenValid((String) any(), (String) any());
        verify(jWTTokenProvider).getSubject((String) any());
        verify(jWTTokenProvider).getAuthentication((String) any(), (HttpServletRequest) any());
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }

    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal13() throws IOException, ServletException {
        when(jWTTokenProvider.getAuthentication((String) any(), (HttpServletRequest) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(jWTTokenProvider.isTokenValid((String) any(), (String) any())).thenReturn(false);
        when(jWTTokenProvider.getSubject((String) any())).thenReturn("Hello from the Dreaming Spires");
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, response, filterChain);
        verify(jWTTokenProvider).isTokenValid((String) any(), (String) any());
        verify(jWTTokenProvider).getSubject((String) any());
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
    }


    /**
     * Method under test: {@link JwtAuthorizationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal16() throws IOException, ServletException {
        when(jWTTokenProvider.getAuthentication((String) any(), (HttpServletRequest) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(jWTTokenProvider.isTokenValid((String) any(), (String) any())).thenReturn(true);
        when(jWTTokenProvider.getSubject((String) any())).thenReturn("Hello from the Dreaming Spires");
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        when(defaultMultipartHttpServletRequest.getMethod()).thenReturn("OPTIONS");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        jwtAuthorizationFilter.doFilterInternal(defaultMultipartHttpServletRequest, mockHttpServletResponse, filterChain);
        verify(defaultMultipartHttpServletRequest).getMethod();
        verify(filterChain).doFilter((ServletRequest) any(), (ServletResponse) any());
        assertEquals(200, mockHttpServletResponse.getStatus());
    }
}

