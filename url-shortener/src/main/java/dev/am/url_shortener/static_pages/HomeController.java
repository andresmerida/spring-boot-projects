package dev.am.url_shortener.static_pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // model.addAttribute("title", "URL Shortner");
        model.addAttribute("welcomeMessage", "Hello from Spring Boot 4!");
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
