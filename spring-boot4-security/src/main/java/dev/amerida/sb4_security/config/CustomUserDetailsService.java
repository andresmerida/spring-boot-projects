package dev.amerida.sb4_security.config;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.ConcurrentHashMap;

class CustomUserDetailsService implements UserDetailsService {
    private final ConcurrentHashMap<String, CustomUser> users = new ConcurrentHashMap<>();

    CustomUserDetailsService(CustomUser... users) {
        for (CustomUser user : users) {
            this.users.put(user.getUsername(), user);
        }
    }

    @Override
    @NullMarked
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = users.get(username);
        if (user == null || !user.getUsername().equals(username)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new CustomUser(
                user.getUsername(),
                user.getPassword(),
                user.email(),
                user.getAuthorities(),
                user.isEnabled()
        );
    }
}
