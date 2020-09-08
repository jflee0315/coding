package com.sidecarhealth.assignmemt.coding.security;

import com.auth0.jwt.JWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            logger.info("Authenticating.....");
            String username;
            String password;
            try {
                String basicAuth = request.getHeader("Authorization").trim();
                String authString = new String(Base64.getDecoder().decode(basicAuth.split(" ")[1]));
                String[] authInfo = authString.split(":");
                username = authInfo[0];
                password = authInfo[1];
            } catch (Exception e) {
                username = "";
                password = "";
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authentication) {
        logger.info("Successful auth!!");
        User user = (User)authentication.getPrincipal();

        String token = JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(HMAC512("secret".getBytes()));
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

}
