package com.university.scooterrental.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationViewController {

    @GetMapping("/view/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @GetMapping("view/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

}
