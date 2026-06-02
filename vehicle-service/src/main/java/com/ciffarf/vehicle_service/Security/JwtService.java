package com.ciffarf.vehicle_service.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {


    private static final String SECRET =
            "mySuperSecretKeyForJwtAuthentication2026SecureKey";


    public String generateToken(
            String username,
            String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 3000000))
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET)
                .compact();
    }

    public String extractUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(
            String token,
            String username
    ){
        String extractedUsername= extractUsername(token);
        return extractedUsername.equals(username);
    }
}
