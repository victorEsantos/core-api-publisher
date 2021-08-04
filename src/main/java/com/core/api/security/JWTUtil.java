package com.core.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static java.util.Objects.nonNull;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(String userName) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MILLISECOND, expiration);
        cal.getTime();

        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

    }

    public boolean tokenValido(String token) {
        var claims = getClaims(token);
        if (nonNull(claims)) {
            var username = claims.getSubject();
            var expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (nonNull(username) && nonNull(expirationDate) && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String token) {
        var claims = getClaims(token);
        String username = null;

        if (nonNull(claims)) {
            username = claims.getSubject();
        }

        return username;
    }
}
