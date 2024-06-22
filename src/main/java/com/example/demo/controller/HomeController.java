package com.example.demo.controller;

import com.example.demo.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserSession userSession;

    @GetMapping("/")
    public String home(Model model) {
        if(userSession.getUsername()==null) return "redirect:/login";
        model.addAttribute("message", "Hello, Springs Boot with JSP!");
        model.addAttribute("userSession", userSession);
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        // Your authentication logic goes here (e.g., check username/password against database)
        System.out.println("username: " + username + ", password: " + password);
        if ("err".equals(username) && "123".equals(password)) {
            userSession.setUsername(username);
            return "redirect:/";
        } else {
            return "redirect:/login?error=invalid username or password";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        userSession.setUsername(null);
        return "redirect:/login";
    }
}
