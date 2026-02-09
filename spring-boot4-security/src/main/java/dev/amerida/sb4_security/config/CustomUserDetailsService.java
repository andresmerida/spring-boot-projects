package dev.amerida.sb4_security.config;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.ConcurrentHashMap;

class CustomUserDetailsService implements UserDetailsService {
    private final ConcurrentHashMap<String, UserDetails> users = new ConcurrentHashMap<>();

    CustomUserDetailsService(UserDetails... users) {
        for (UserDetails user : users) {
            this.users.put(user.getUsername(), user);
        }
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null || !user.getUsername().equals(username)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities()
        );
    }
}
