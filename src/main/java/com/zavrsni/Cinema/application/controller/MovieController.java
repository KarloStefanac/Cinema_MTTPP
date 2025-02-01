package com.zavrsni.Cinema.application.controller;

import com.zavrsni.Cinema.application.service.HallService;
import com.zavrsni.Cinema.application.service.MovieService;
import com.zavrsni.Cinema.rest_api.dto.HallDto;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final HallService hallService;

    public MovieController(MovieService movieService, HallService hallService) {
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @GetMapping("/home")
    public String homePage(){
        return "landingPage";
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "index";
    }

    @GetMapping("/movie")
    public String showMovieForm(Model model) {
        MovieRequest movieRequest = new MovieRequest();
        model.addAttribute("movie", movieRequest);

        return "addMovie";
    }

    @PostMapping("/movie/save")
    public String saveMovie(@ModelAttribute("movie") MovieRequest movieRequest) {
        movieService.addMovie(movieRequest);
        return "redirect:/movie?success";
    }

    @GetMapping("/movie/{id}")
    public String showMovieScreenings(@PathVariable Long id, Model model){
        List<ScreeningDto> screenings = movieService.getMovieScreenings(id);

        model.addAttribute("screenings", screenings);
        model.addAttribute("id", id);
        return "movieScreenings";
    }

    @GetMapping("/movie/{id}/screening")
    public String showScreeningForm(@PathVariable Long id, Model model){
        ScreeningDto screeningRequest = new ScreeningDto();
        List<HallDto> halls = hallService.getAllHalls();
        model.addAttribute("halls", halls);
        model.addAttribute("screening", screeningRequest);
        return "addScreening";
    }

}
