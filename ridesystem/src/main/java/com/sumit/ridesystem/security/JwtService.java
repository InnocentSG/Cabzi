package com.sumit.ridesystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import java.security.Key;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.function.Function;

@Service

public class JwtService {

    // =================================
    // SECRET KEY
    // =================================

    @Value("${jwt.secret}")

    private String secretKey;

    // =================================
    // JWT EXPIRATION
    // =================================

    @Value("${jwt.expiration}")

    private long jwtExpiration;

    // =================================
    // SIGNING KEY
    // =================================

    private Key getSigningKey() {

        if (
                secretKey == null
                        || secretKey.getBytes(StandardCharsets.UTF_8).length < 32
        ) {
            throw new IllegalStateException(
                    "jwt.secret must be at least 32 bytes for HS256"
            );
        }

        return Keys.hmacShaKeyFor(

                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    // =================================
    // EXTRACT EMAIL
    // =================================

    public String extractEmail(
            String token
    ) {

        return extractClaim(

                token,

                Claims::getSubject
        );
    }

    // =================================
    // EXTRACT EXPIRATION
    // =================================

    public Date extractExpiration(
            String token
    ) {

        return extractClaim(

                token,

                Claims::getExpiration
        );
    }

    // =================================
    // EXTRACT CLAIM
    // =================================

    public <T> T extractClaim(

            String token,

            Function<Claims, T> resolver
    ) {

        final Claims claims =
                extractAllClaims(token);

        return resolver.apply(claims);
    }

    // =================================
    // EXTRACT ALL CLAIMS
    // =================================

    private Claims extractAllClaims(
            String token
    ) {

        return Jwts

                .parserBuilder()

                .setSigningKey(
                        getSigningKey()
                )

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    // =================================
    // TOKEN EXPIRED
    // =================================

    private boolean isTokenExpired(
            String token
    ) {

        return extractExpiration(token)

                .before(new Date());
    }

    // =================================
    // GENERATE TOKEN
    // =================================

    public String generateToken(

            UserDetails userDetails
    ) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put(

                "role",

                userDetails

                        .getAuthorities()

                        .iterator()

                        .next()

                        .getAuthority()
        );

        return createToken(

                claims,

                userDetails.getUsername()
        );
    }

    // =================================
    // CREATE TOKEN
    // =================================

    private String createToken(

            Map<String, Object> claims,

            String subject
    ) {

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(subject)

                .setIssuedAt(

                        new Date(
                                System.currentTimeMillis()
                        )
                )

                .setExpiration(

                        new Date(

                                System.currentTimeMillis()

                                        + jwtExpiration
                        )
                )

                .signWith(

                        getSigningKey(),

                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    // =================================
    // VALIDATE TOKEN
    // =================================

    public boolean isTokenValid(

            String token,

            UserDetails userDetails
    ) {

        final String email =
                extractEmail(token);

        return (

                email.equals(
                        userDetails.getUsername()
                )

                        &&

                        !isTokenExpired(token)
        );
    }
}
