package com.sidecarhealth.assignmemt.coding.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImp myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .disable()
                .csrf()
                .disable()
                .addFilter(getJWTAuthenticationFilter())
                .addFilter(getJWTAuthorizationFilter())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private JwtAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    private JwtAuthorizationFilter getJWTAuthorizationFilter() throws Exception {
        final JwtAuthorizationFilter filter = new JwtAuthorizationFilter(authenticationManager());
        return filter;
    }
}