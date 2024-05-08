package com.resired.api.security.application.usecase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class Jwt {

    //TODO change it for private key (pem)
    private static final String SECRET_KEY = "5E8C1D0E9A3F6B5C9D0A7E6B1A3D9E2A6E2A4C1E6B2A8C2D7D8D2C6D6C2A2E5"; // Clave secreta para firmar el JWT
    private static final long EXPIRATION_TIME = 86400000; // Tiempo de expiración del JWT (en milisegundos, por ejemplo, 1 día)

    public static String generateToken(String profile, String documentId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder().subject(profile).issuedAt(now)
            .expiration(expiration).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .claim("userId", documentId)
            .compact();
    }
}
