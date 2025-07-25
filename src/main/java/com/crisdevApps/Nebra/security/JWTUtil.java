package com.crisdevApps.Nebra.security;

import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtil {
    @Value("${jwt.token.secret}")
    private String jwtKey;
    @Value("${jwt.token.expiration}")
    private int tokenExpiration;
    @Value("${jwt.refresh.expiration}")
    private int refreshTokenExpiration;
    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String GenerateToken(UUID id, String email, boolean refresh, String sessionId, UserRole role){
        long expirationInMs = refresh ? refreshTokenExpiration : tokenExpiration;
        Claims claims = Jwts.claims()
                .setId(id.toString())
                .setSubject(email)
                .setExpiration(new Date((new Date().getTime() + expirationInMs)))
                .setIssuedAt(Date.from(Instant.now()));
        claims.put("role", role.toString());

        if(sessionId != null && refresh){
            claims.put("sessionId", sessionId);
        }

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String GetEmailFromToken(String token, boolean refresh){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public UUID GetSessionIdFromRefreshToken(String token){

        try {
            String sessionId =Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("sessionId", String.class);

            return UUID.fromString(sessionId);
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
            throw new ValidationException("Invalid signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            throw new ValidationException("Invalid token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
            throw new ValidationException("Session expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
            throw new ValidationException("Invalid token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
            throw new ValidationException("Invalid token");
        }
    }

    public String GetUserRoleFromToken(String token){
        try {

            return Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class);
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
            throw new ValidationException("Invalid signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            throw new ValidationException("Invalid token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
            throw new ValidationException("Session expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
            throw new ValidationException("Invalid token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
            throw new ValidationException("Invalid token");
        }
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean ValidateRecoverToken(String token, String userEmail){
        try {
            final String username = extractUsername(token);
            return (username.equals(userEmail) && !isTokenExpired(token));
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }


    public boolean ValidateJwtToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
            throw new ValidationException("Invalid signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            throw new ValidationException("Invalid token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
            throw new ValidationException("Token expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
            throw new ValidationException("Invalid token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
            throw new ValidationException("Invalid token");
        }
    }
}
