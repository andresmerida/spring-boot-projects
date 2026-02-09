package dev.amerida.sb4_security.web.static_pages;

import dev.amerida.sb4_security.config.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private String username = "Anonymous";

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null) {
            username = authentication.getName().toUpperCase();
        }

        return ("""
                <center>
                <h1>Spring Boot Security Tutorial</h1>
                <p>Logged in as %s</p>
                <a href='/public'>Public</a><br/>
                <a href='/user'>Private for User</a><br/>
                <a href='/admin'>Private for Admin</a>
                </center>
                """).formatted(username);
    }

    @GetMapping("/public")
    public String public_page(Authentication authentication) {
        if (authentication != null) {
            username = authentication.getName().toUpperCase();
        }

        return ("""
                <center>
                <h1>Spring Boot Tutorial</h1>
                <h2>Public Page!</h2>
                <p>Logged in as %s</p>
                <a href='/'>Home</a>
                </center>
                """).formatted(username);
    }

    @GetMapping("/user")
    public String private_page_user(@AuthenticationPrincipal CustomUser customUser) {
        return ("""
                <center>
                <h1>Spring Boot Tutorial</h1>
                <h2>Private Page for User!</h2>
                <p>Logged in as: %s</p>
                <p>E-mail: %s</p>
                <p>Role: %s</p>
                <a href='/'>Home</a>
                </center>
                """).formatted(customUser.username(), customUser.email(), customUser.getAuthorities().toString());
    }

    @GetMapping("/admin")
    public String private_page_admin(@AuthenticationPrincipal CustomUser customUser) {
        return ("""
                <center>
                <h1>Spring Boot Tutorial</h1>
                <h2>Private Page for Admin!</h2></br>
                <p>Logged in as: %s</p>
                <p>E-mail: %s</p>
                <p>Roles: %s</p>
                <a href='/'>Home</a>
                </center>
                """)
                .formatted(
                        customUser.username().toUpperCase(),
                        customUser.email(),
                        customUser.getAuthorities().toString()
                );
    }
}
