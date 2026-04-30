package dev.mr3.sb.controller;


import dev.mr3.sb.model.User;
import dev.mr3.sb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
// Handles login page rendering and basic login form submission.
public class Login{
    private final UserService userService;

    Login(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/login")
    public String loginPage() {
        return "Login";
    }

@PostMapping("/login")
    public String login(User user) {
    if (!userService.validateLogin(user)) {
        return "Login";
    }
    return "PatientDashboard";
}

}
