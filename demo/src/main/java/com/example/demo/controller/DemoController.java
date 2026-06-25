package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @GetMapping(value = "/controller/message", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String greeting() {
        return "Welcome to Spring Boot with controller!";
    }
}
