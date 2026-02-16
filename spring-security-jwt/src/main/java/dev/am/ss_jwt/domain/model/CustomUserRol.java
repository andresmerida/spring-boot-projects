package dev.am.ss_jwt.domain.model;

public record CustomUserRol(
        String username,
        String name,
        String email,
        String password,
        boolean enabled,
        String authority) {
}
