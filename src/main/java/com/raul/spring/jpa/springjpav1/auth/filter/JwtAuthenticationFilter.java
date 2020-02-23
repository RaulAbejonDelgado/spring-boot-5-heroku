package com.raul.spring.jpa.springjpav1.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raul.spring.jpa.springjpav1.auth.service.JwtService;
import com.raul.spring.jpa.springjpav1.auth.service.JwtServiceImpl;
import com.raul.spring.jpa.springjpav1.models.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        //to change to login url
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));

        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null && password == null) {
            try {

                UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
                username = user.getUserName();
                password = user.getPassword();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
                                            throws IOException, ServletException {

        String token = jwtService.create(authResult);

        response.addHeader(JwtServiceImpl.HEADER_STRING, JwtServiceImpl.TOKEN_PREFIX+jwtService.create(authResult));

        Map<String, Object> body = new HashMap<>();
        body.put("token",token);
        body.put("user", authResult.getPrincipal());
        body.put("message", authResult.getName().concat(" authenticated successfully"));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        Map<String, Object> body = new HashMap<>();

        body.put("message", "Authentication failure");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");

        //super.unsuccessfulAuthentication(request, response, failed);
    }
}
