package dev.amerida.sb4_security.config;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public record CustomUser(
        String username,
        String password,
        String email,
        Collection<? extends GrantedAuthority> roles,
        boolean enabled
) implements org.springframework.security.core.userdetails.UserDetails {

    public static Builder builder() {
        return new Builder();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return this.username;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public static class Builder {
        private String username;
        private String password;
        private String email;
        private Collection<? extends GrantedAuthority> roles;
        private boolean enabled;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder roles(String... roles) {
            Set<GrantedAuthority> authorities = new HashSet<>(roles.length);
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            this.roles = authorities;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomUser build() {
            return new CustomUser(username, password, email, roles, enabled);
        }
    }
}
