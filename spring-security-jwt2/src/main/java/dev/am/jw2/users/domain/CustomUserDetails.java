package dev.am.jw2.users.domain;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public record CustomUserDetails(
        String username,
        String name,
        String email,
        String password,
        boolean enabled,
        Set<String> roles
) implements UserDetails {

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return password();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return username();
    }

    @Override
    public boolean isEnabled() {
        return enabled();
    }
}
