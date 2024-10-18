package com.resired.api.security.application.usecase;

import com.resired.api.security.domain.entity.UserApp;
import com.resired.api.security.domain.service.JwtSecurity;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtSecurity jwtSecurity;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UserApp extractUser(String bearer) {
        String token = bearer.substring(7);
        Claims claims = extractAllClaims(token);
        return UserApp.generateUserAppFromClaims(claims);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String regenerateToken(String token, int hoursDuration) {
        String userName = extractClaim(token, Claims::getSubject);
        return jwtSecurity.regenerateToken(token, userName, obtainExpirationDate(hoursDuration));
    }

    private Claims extractAllClaims(String token) {
        return jwtSecurity.extractAllClaims(token);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public String generateToken(String username, int hoursDuration) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, hoursDuration);
    }

    public String generateToken(String username, String rol, Integer neighborhood, Integer homeId, Integer userId, int hoursDuration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);
        claims.put("neighborhoodId", neighborhood);
        claims.put("homeId", homeId);
        claims.put("userId", userId);
        return createToken(claims, username, hoursDuration);
    }

    private String createToken(Map<String, Object> claims, String username, int hoursDuration) {
        Date issuedAt = new Date(System.currentTimeMillis());
        return jwtSecurity.generateToken(username, claims, issuedAt, obtainExpirationDate(hoursDuration));
    }

    private Date obtainExpirationDate(int hoursDuration) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, hoursDuration);
        return instance.getTime();
    }
}
