package com.zavrsni.Cinema.application.controller;

import com.zavrsni.Cinema.application.service.ScreeningService;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.ScreeningRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/screening/")
public class ScreeningController {
    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping("/save")
    public String addScreening(@ModelAttribute("screening") ScreeningRequest screeningRequest,
                               @RequestParam("hallId") Long hallId,
                               @RequestParam("movieId") Long movieId) {

        screeningService.addScreening(screeningRequest, movieId, hallId);
        return "redirect:/movie/" + movieId;
    }

    @GetMapping("/{screeningId}")
    public String showScreeningReservation(@PathVariable Long screeningId, Model model) {
        ScreeningDto screeningDto = screeningService.getScreening(screeningId);
        List<Integer> seatList = screeningService.getSeats(screeningId);

        model.addAttribute("id", screeningId);
        model.addAttribute("hallDto", screeningDto.getHallDto());
        model.addAttribute("seatList", seatList);

        return "screeningReservation";
    }

    @PostMapping("/ticket")
    public String saveScreeningReservation(@RequestParam("seatId") List<Long> seatIds,
                                           @RequestParam("screeningId") Long screeningId, Model model) {
        ScreeningDto screeningDto = screeningService.getScreening(screeningId);
        Double totalPrice = screeningService.getTotalPrice((long) seatIds.size(), screeningDto.getMovieDto().getPrice());
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("screeningDto", screeningDto);
        model.addAttribute("seats", seatIds);
        return "ticket";
    }
}
