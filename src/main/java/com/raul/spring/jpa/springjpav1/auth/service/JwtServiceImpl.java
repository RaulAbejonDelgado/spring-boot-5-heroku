package com.raul.spring.jpa.springjpav1.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raul.spring.jpa.springjpav1.models.service.auth.SimpleGrantedAuthorityMixin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {


    SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public long HOUR_MILISECONDS = 3600000 ;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";



    @Override
    public String create(Authentication auth) throws JsonProcessingException {

        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(auth.getName())
                    .signWith(SECRET_KEY )
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + HOUR_MILISECONDS))
                    .compact();

        return token;

    }

    @Override
    public boolean validate(String token) {


        try{

            getClaims(token);

            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey( SECRET_KEY)
                .build()
                .parseClaimsJws(resolve(token))
                .getBody();

        return claims;
    }

    @Override
    public String getUserUsername(String token) throws IOException {

        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities =
                Arrays.asList(
                        new ObjectMapper()
                                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                                .readValue(
                                        roles.toString().getBytes(),
                                        SimpleGrantedAuthority[].class
                                )
                );

        return authorities;
    }

    @Override
    public String resolve(String token) {
        if(token != null && token.startsWith(TOKEN_PREFIX)){
            return token.replace(TOKEN_PREFIX,"");
        }
        return null;
    }
}
