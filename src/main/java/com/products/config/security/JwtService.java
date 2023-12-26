package com.products.config.security;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${secret.key}")
    private String SecretKey;



    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        var roles = userDetails.getAuthorities();
        List<String> rolesString = roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        extraClaims.put("roles", rolesString);

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())//username de usuario (email)
            .issuedAt(Date.from(Instant.now())) //fecha de creación
            .expiration(Date.from(Instant.now().plus(Duration.ofHours(3)))) //fecha de vencimiento
            .signWith(getSingInKey(), SignatureAlgorithm.HS256) //algoritmo de firma
            .compact();

    }

    public String getUserName(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(getSingInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    
    public boolean validarToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).isBefore(Instant.now());
    }

    private Instant getExpiration(String token) {
        return getClaim(token, Claims::getExpiration).toInstant();
    }
    
}
