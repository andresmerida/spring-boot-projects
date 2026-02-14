package dev.am.sb4_security_2.web.controller;

import dev.am.sb4_security_2.domain.CustomUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String privateUserPage(Model model, @AuthenticationPrincipal CustomUser user) {
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String privateAdminPage(Model model, @AuthenticationPrincipal CustomUser user) {
        model.addAttribute("user", user);
        return "admin";
    }
}
