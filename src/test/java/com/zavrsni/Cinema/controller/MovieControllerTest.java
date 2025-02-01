package com.zavrsni.Cinema.controller;

import com.zavrsni.Cinema.application.controller.MovieController;
import com.zavrsni.Cinema.application.service.HallService;
import com.zavrsni.Cinema.application.service.MovieService;
import com.zavrsni.Cinema.persistence.entity.Genre;
import com.zavrsni.Cinema.rest_api.dto.HallDto;
import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private HallService hallService;

    @BeforeEach
    void setUp() {
        // Mock data setup
        MovieDto movie1 = new MovieDto();
        movie1.setId(1L);
        movie1.setName("Inception");
        movie1.setDirector("Christopher Nolan");
        movie1.setDuration(148);
        movie1.setGenres(List.of(Genre.Thriller));
        movie1.setPrice(15);
        movie1.setImage("InceptionImage");

        MovieDto movie2 = new MovieDto();
        movie2.setId(2L);
        movie2.setName("Interstellar");
        movie2.setDirector("Christopher Nolan");
        movie2.setDuration(169);
        movie2.setGenres(List.of(Genre.Sci_fi));
        movie2.setPrice(29);
        movie1.setImage("InterstellarImage");

        List<MovieDto> movies = Arrays.asList(movie1, movie2);
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);


        HallDto hall1 = new HallDto();
        hall1.setId(1L);
        hall1.setName("Hall A");
        hall1.setCapacity(60);

        HallDto hall2 = new HallDto();
        hall2.setId(2L);
        hall2.setName("Hall B");
        hall2.setCapacity(90);

        List<HallDto> halls = Arrays.asList(hall1, hall2);
        Mockito.when(hallService.getAllHalls()).thenReturn(halls);

        ScreeningDto screening1 = new ScreeningDto();
        screening1.setId(1L);
        screening1.setTime(LocalDateTime.now().plusDays(1));
        screening1.setMovieDto(movie1);
        screening1.setHallDto(hall1);

        ScreeningDto screening2 = new ScreeningDto();
        screening2.setId(2L);
        screening2.setTime(LocalDateTime.now().plusDays(2));
        screening2.setMovieDto(movie1);
        screening2.setHallDto(hall2);

        List<ScreeningDto> screenings = Arrays.asList(screening1, screening2);
        Mockito.when(movieService.getMovieScreenings(1L)).thenReturn(screenings);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("landingPage"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("movies"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER,ADMIN"})
    void testShowMovieForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movie"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("addMovie"))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/movie/save")
                        .param("name", "Inception")
                        .param("director", "Christopher Nolan")
                        .param("duration", "148")
                        .param("genres", "Action")
                        .param("price", "20")
                        .param("image", "inception.jpg")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/movie?success"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testShowMovieScreenings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("movieScreenings"))
                .andExpect(model().attributeExists("screenings"))
                .andExpect(model().attributeExists("id"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER,ADMIN"})
    void testShowScreeningForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movie/1/screening"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("addScreening"))
                .andExpect(model().attributeExists("halls"))
                .andExpect(model().attributeExists("screening"));
    }
}
