package dev.amerida.sb4_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        return http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/public").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();
    }

    /**
     * Provides an in-memory implementation of the {@link UserDetailsService} for user authentication.
     * The service is pre-configured with two users: "user" with the role "USER" and "admin" with roles
     * "USER" and "ADMIN". Passwords are encoded using a delegating password encoder.
     *
     * @return an instance of {@link InMemoryUserDetailsManager} pre-configured with default user details.
     */
    @Bean
    UserDetailsService userDetailsService() {
        // Create a default encoder (Standard spring security behavior spring boot 4+) before saving in the memory map
        CustomUser user = CustomUser.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .email("user@gmail.com")
                .roles("USER")
                .enabled(true)
                .build();
        CustomUser admin = CustomUser.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .email("admin@gmial.com")
                .roles("USER", "ADMIN")
                .enabled(true)
                .build();

        IO.println("User password: " + user.getPassword());
        IO.println("Admin password: " + admin.getPassword());

        return new CustomUserDetailsService(user, admin);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
