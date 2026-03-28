package com.newlynest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Always show home page — authenticated users can use the nav to go to Dashboard
        return "home";
    }
}
