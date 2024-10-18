package com.resired.api.security.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtSecurity {

    private final PublicKey publicKey = loadPublicKey();
    private final PrivateKey privateKey = loadPrivateKey();

    public JwtSecurity() throws Exception {

    }

    public String generateToken(String username, Map<String, Object> claims, Date issuedAt, Date expiration) {
        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(privateKey)
            .compact();
    }


    public void validateJwt(String jwt) {
        Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(jwt);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
            .parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String regenerateToken(String token, String username, Date expiration) {
        Date now = new Date(System.currentTimeMillis());
        Claims payload = Jwts
            .parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return Jwts.builder()
            .claims(payload)
            .subject(username)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(privateKey)
            .compact();
    }

    private PrivateKey loadPrivateKey() throws Exception {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("private_key.pem");
        String privateKeyContent = new String(resourceAsStream.readAllBytes())
            .replaceAll("\\R", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private PublicKey loadPublicKey() throws Exception {

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("public_key.pem");
        String publicKeyContent = new String(resourceAsStream.readAllBytes())
            .replaceAll("\\R", "")
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
