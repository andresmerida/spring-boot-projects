package dev.am.sb4_security_2.domain;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final CustomUserRepository customUserRepository;


    @Override
    @NullMarked
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }
}
