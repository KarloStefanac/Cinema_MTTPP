package com.zavrsni.Cinema.application.controller;

import com.zavrsni.Cinema.application.service.ScreeningService;
import com.zavrsni.Cinema.application.service.TicketService;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TicketController {
    private final TicketService ticketService;
    private final ScreeningService screeningService;

    public TicketController(TicketService ticketService, ScreeningService screeningService) {
        this.ticketService = ticketService;
        this.screeningService = screeningService;
    }

    @PostMapping("/ticket/save")
    public String saveTicket(@RequestParam("seatIds") List<Long> seatIds,
                             @RequestParam("screeningId") Long screeningId){
        ScreeningDto screeningDto = screeningService.addReservation(screeningId, seatIds);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ticketService.addTickets(seatIds, screeningId, username);

        return "redirect:/";
    }
}
