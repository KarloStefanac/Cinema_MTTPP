package com.zavrsni.Cinema.application.controller;

import com.zavrsni.Cinema.application.service.TicketService;
import com.zavrsni.Cinema.application.service.UserService;
import com.zavrsni.Cinema.rest_api.dto.TicketDto;
import com.zavrsni.Cinema.rest_api.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;

    public UserController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto userDto = userService.getUser(username);
        List<TicketDto> tickets = ticketService.getUserTickets(userDto);
        model.addAttribute("user", userDto);
        model.addAttribute("tickets", tickets);
        return "profile";
    }
}
