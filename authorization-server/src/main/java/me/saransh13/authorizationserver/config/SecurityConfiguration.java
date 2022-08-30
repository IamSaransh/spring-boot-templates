package me.saransh13.authorizationserver.config;

import me.saransh13.authorizationserver.constant.SecurityConstant;
import me.saransh13.authorizationserver.filter.JWTAuthenticationEntryPoint;
import me.saransh13.authorizationserver.filter.JwtAccessDeniedHandler;
import me.saransh13.authorizationserver.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author saransh
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JwtAuthorizationFilter jwtAuthorizationFilter;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public  SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                  JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                  JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                @Qualifier("userDetailsService") UserDetailsService userDetailsService){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected  void configure(HttpSecurity http) throws  Exception{
        http
                .csrf().disable().
                cors().
                and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().
                authorizeRequests()
                .antMatchers(SecurityConstant.PUBLIC_URLS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        //added to show H2-console @Stackoverflow answer
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
