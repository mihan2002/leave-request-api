package com.mihan.leave_request_api.utility;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static  final  String SECRET_KEY ="eyJzdWIiOiJtaWhhbiIsImlhdCI6MTc0OTYxNDkzNCwiZXhwIjoxNzQ5NjUwOTM0fQsJrWxE8NyN5yYK2b27F1fgFELsLpWQYQCdO7BaeyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaF4sLg=\n";

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 1;

    public String generateToken(String username,String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
