package dev.am.jw2.common;

import dev.am.jw2.users.domain.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private Duration tokenExpirationTime;

    public String generateToken(Map<String, Object> claims, String subject) {
        log.info("Generating token for subject {}, secretKey: {}", subject, secretKey);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationTime.toMillis()))
                .claims(claims)
                .signWith(getSignedKey())
                .compact();
    }

    public String generateToken(CustomUserDetails userDetails) {
        return generateToken(Map.of("authorities", userDetails.roles()),
                userDetails.getUsername());
    }

    public Claims verifySignatureAndExtractClaims(String token) {
        return Jwts.parser()
                // Verify the signature using your secret key
                .verifyWith(getSignedKey())
                .build()    // Build the parser
                .parseSignedClaims(token)   // Parser the JWS (Signed JWT), this will throw an exception if the signature is invalid
                .getPayload();
    }

    public String extractUsername(String token) {
        return verifySignatureAndExtractClaims(token).getSubject();
    }

    public Date extractExpirationDate(String token) {
        return verifySignatureAndExtractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public Collection<? extends GrantedAuthority> extractRoles(String token) {
        var authoritiesClaim = verifySignatureAndExtractClaims(token).get("authorities");

        if (authoritiesClaim instanceof Collection<?> authorities) {
            return authorities.stream()
                    .map(Object::toString)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        }

        return Collections.emptyList();
    }

    private @NonNull SecretKey getSignedKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
