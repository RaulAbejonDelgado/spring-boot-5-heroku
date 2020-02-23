package com.raul.spring.jpa.springjpav1.auth.filter;

import com.raul.spring.jpa.springjpav1.models.service.auth.service.JwtService;
import com.raul.spring.jpa.springjpav1.models.service.auth.service.JwtServiceImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(JwtServiceImpl.HEADER_STRING);

        if (!requiresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }


        UsernamePasswordAuthenticationToken authenticationToken = null;

        if(jwtService.validate(header)){
            String userName = jwtService.getUserUsername(header);

            authenticationToken = new UsernamePasswordAuthenticationToken(
                    userName,null,jwtService.getRoles(header)
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    protected boolean requiresAuthentication(String header) {
        if (header == null || !header.startsWith(JwtServiceImpl.TOKEN_PREFIX)) {
            return false;
        }
        return true;
    }

}
