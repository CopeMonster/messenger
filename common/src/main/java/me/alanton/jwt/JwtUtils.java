package me.alanton.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static String generateToken(Map<String, Object> claims, String subject, String secret, Long expirationSeconds) {
        Instant expiration = LocalDateTime.now()
                .plusSeconds(expirationSeconds)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .expiration(Date.from(expiration))
                .signWith(getSigningKey(secret))
                .compact();
    }

    public static boolean isValid(String token, String username, String secret) {
        String extractedUsername = extractUsername(token, secret);
        return extractedUsername.equals(username) && !isExpired(token, secret);
    }

    public static String extractUsername(String token, String secret) {
        return extractAllClaims(token, secret).getSubject();
    }

    public static boolean isExpired(String token, String secret) {
        return extractAllClaims(token, secret).getExpiration().before(new Date());
    }

    private static Claims extractAllClaims(String token, String secret) {
        return Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static SecretKey getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}