package dev.am.ss_jwt.domain;

import dev.am.ss_jwt.domain.model.CustomUserRol;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CustomUser> findByUsername(String username) {
        List<CustomUserRol> list = jdbcTemplate
                .query("""
                                SELECT u.username, u.name, u.email, u.password, u.enabled, a.authority
                                FROM users u
                                INNER JOIN authorities a ON u.username = a.username
                                WHERE u.username = ?
                                """,
                        (rs, rowNum) -> new CustomUserRol(
                                rs.getString("username"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getBoolean("enabled"),
                                rs.getString("authority")
                        ), username
                );
        if (!list.isEmpty()) {
            CustomUserRol customUserRol = list.getFirst();
            return Optional.of(new CustomUser(
                    customUserRol.username(),
                    customUserRol.name(),
                    customUserRol.email(),
                    customUserRol.password(),
                    customUserRol.enabled(),
                    list.stream()
                            .map(CustomUserRol::authority)
                            .toList()
            ));
        } else {
            return Optional.empty();
        }
    }
}
