package com.bankmanagement.banksystem.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {
    
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    // generate JWT token
    public String generateToken (Authentication authentication) {
        
        String userName = authentication.getName();

        Date currentDate = new Date();

        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
        
        String token = Jwts.builder().subject(userName).issuedAt(new Date()).expiration(expirationDate).signWith(key()).compact();
        
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from JWT token
    public String getUserName(String token) {

        return Jwts.parser().
                    verifyWith((SecretKey) key()).
                    build().
                    parseSignedClaims(token).
                    getPayload().
                    getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().
             verifyWith((SecretKey) key()).
             build().
             parse(token);
             
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
