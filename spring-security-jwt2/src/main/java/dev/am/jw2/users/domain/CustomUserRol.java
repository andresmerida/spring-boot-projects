package dev.am.jw2.users.domain;

public record CustomUserRol(
        String username,
        String name,
        String email,
        String password,
        boolean enabled,
        String authority
) {
}
