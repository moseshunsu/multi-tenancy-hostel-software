package net.hostelHub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    public String demo() {
        return "demo";
    }

}
