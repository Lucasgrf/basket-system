package com.sporthub.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sporthub.api.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private static final String ISSUER = "sporthub-api";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_USER_ID = "userId";

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-hours}")
    private int expirationHours;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withClaim(CLAIM_USER_ID, user.getId())
                    .withClaim(CLAIM_ROLE, user.getRole().name())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating authentication token", exception);
        }
    }

    /**
     * Validates the token and returns the username (subject) if valid, or null if invalid.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    /**
     * Decodes a raw token without verifying the signature. Use only for extracting claims
     * after validation has already been performed.
     */
    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    private Instant generateExpirationDate() {
        return Instant.now().plus(expirationHours, java.time.temporal.ChronoUnit.HOURS);
    }
}
