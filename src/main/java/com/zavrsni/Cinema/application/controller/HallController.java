package com.zavrsni.Cinema.application.controller;

import com.zavrsni.Cinema.application.service.HallService;
import com.zavrsni.Cinema.rest_api.request.HallRequest;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("halls/")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public String getHall(@RequestParam(name = "hallId")Long hallId, Model model) {
        model.addAttribute("hall", hallService.getHallById(hallId));
        return "index";
    }

    @GetMapping("/all")
    public String getHalls(Model model) {
        model.addAttribute("halls", hallService.getAllHalls());
        return "index";
    }

    @GetMapping("hall")
    public String showMovieForm(Model model) {
        HallRequest hallRequest = new HallRequest();
        model.addAttribute("hall", hallRequest);

        return "addHall";
    }


    @PostMapping("/save")
    public String saveMovie(@ModelAttribute("movie") HallRequest hallRequest) {
        hallService.addHall(hallRequest);
        return "redirect:/halls/hall?success";
    }
}
