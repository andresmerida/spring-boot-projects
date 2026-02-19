package dev.am.jw2.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        // 1. Check if the header is present and starts with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract the token from the header
        token = authHeader.substring(7);

        try {
            // 3. Extract claims from the token
            username = jwtService.extractUsername(token);

            // 4. Verify if the user is valid and not already authenticated
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Option A: Load from DB (Safest, checks if a user was deleted/banned)
                // UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Option B: Build from Token (Faster, database-free, but can't detect bans immediately)
                // If you chose this, you would construct a CustomUser object directly from 'claims' here.

                // 5. Create Authentication Token
                var authToken = new UsernamePasswordAuthenticationToken(username,
                        null,
                        jwtService.extractRoles(token));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Set Authentication in Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // Token is invalid or expired.
            // We do NOT throw an exception here. We just don't authenticate the user.
            // Spring Security will handle with 403 Forbidden later if the endpoint is protected.
            SecurityContextHolder.clearContext();
        }

        // 7. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
