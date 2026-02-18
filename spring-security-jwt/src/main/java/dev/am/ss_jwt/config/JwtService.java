package dev.am.ss_jwt.config;

import dev.am.ss_jwt.domain.CustomUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Duration tokenExpirationTime;

    public String generateToken(CustomUser userDetails) {
        return generateToken(Map.of("authorities", userDetails.roles()),
                userDetails.getUsername());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        log.info("Secret key: {}", secretKey);
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationTime.toMillis()))
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
