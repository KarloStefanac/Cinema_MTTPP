package com.zavrsni.Cinema.application.controller;

import com.zavrsni.Cinema.application.service.UserService;
import com.zavrsni.Cinema.rest_api.request.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/register/")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserRequest user = new UserRequest();
        user.setStaff(false);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/user")
    public String createUser(@ModelAttribute("user") UserRequest user) {
        userService.addUser(user);
        return "redirect:/?success";
    }
}
