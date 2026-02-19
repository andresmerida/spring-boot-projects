package dev.am.jw2.users.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hi")
    ResponseEntity<String> hi() {
        return ResponseEntity.ok("Hi");
    }

    @RequestMapping("/hello")
    ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

    @RequestMapping("/bye")
    ResponseEntity<String> bye() {
        return ResponseEntity.ok("Bye");
    }
}
