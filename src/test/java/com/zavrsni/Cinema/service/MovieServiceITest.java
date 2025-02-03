package com.zavrsni.Cinema.service;


import com.zavrsni.Cinema.application.service.impl.MovieServiceImpl;
import com.zavrsni.Cinema.persistence.entity.Genre;
import com.zavrsni.Cinema.persistence.entity.MovieEntity;
import com.zavrsni.Cinema.persistence.entity.ScreeningEntity;
import com.zavrsni.Cinema.persistence.repository.MovieRepository;
import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MovieServiceITest {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        // Clean up the database before each test
        movieRepository.deleteAll();
    }
    @Test
    void testAddMovie(){
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setName("Inception");
        movieRequest.setDirector("Christopher Nolan");
        movieRequest.setDuration(148);
        movieRequest.setGenres(List.of(Genre.Thriller));
        movieRequest.setPrice(15);
        movieRequest.setImage("InceptionImage");

        movieService.addMovie(movieRequest);

        List<MovieDto> movies = movieService.getAllMovies();
        assertEquals(1, movies.size());
        assertEquals("Inception", movies.get(0).getName());
        assertEquals("Christopher Nolan", movies.get(0).getDirector());
        assertEquals(148, movies.get(0).getDuration());
        assertEquals(List.of(Genre.Thriller), movies.get(0).getGenres());
        assertEquals(15, movies.get(0).getPrice());
        assertEquals("InceptionImage", movies.get(0).getImage());
    }

    @Test
    void testGetAllMovies(){
        MovieEntity movie1 = new MovieEntity();
        movie1.setName("Inception");
        movie1.setDirector("Christopher Nolan");
        movie1.setDuration(148);
        movie1.setGenres(List.of(Genre.Thriller));
        movie1.setPrice(15);
        movie1.setImage("InceptionImage");
        movieRepository.save(movie1);

        MovieEntity movie2 = new MovieEntity();
        movie2.setName("Interstellar");
        movie2.setDirector("Christopher Nolan");
        movie2.setDuration(169);
        movie2.setGenres(List.of(Genre.Sci_fi));
        movie2.setPrice(29);
        movie1.setImage("InterstellarImage");
        movieRepository.save(movie2);

        List<MovieDto> movies = movieService.getAllMovies();

        assertEquals(2, movies.size());
        assertEquals("Inception", movies.get(0).getName());
        assertEquals("Interstellar", movies.get(1).getName());
    }

    @Test
    void testGetMovieById(){
        MovieEntity movie = new MovieEntity();
        movie.setName("Interstellar");
        movie.setDirector("Christopher Nolan");
        movie.setDuration(169);
        movie.setGenres(List.of(Genre.Sci_fi));
        movie.setPrice(20);
        movie.setImage("InterstellarImage");
        MovieEntity savedMovie = movieRepository.save(movie);

        MovieDto foundMovie = movieService.getMovie(savedMovie.getId());

        assertNotNull(foundMovie);
        assertEquals("Interstellar", foundMovie.getName());
        assertEquals("Christopher Nolan", foundMovie.getDirector());
        assertEquals(169, foundMovie.getDuration());
        assertEquals(List.of(Genre.Sci_fi), foundMovie.getGenres());
        assertEquals(20, foundMovie.getPrice());
        assertEquals("InterstellarImage", foundMovie.getImage());
    }

    @Test
    void testGetMovieScreenings() {
        MovieEntity movie = new MovieEntity();
        movie.setName("Interstellar");
        movie.setDirector("Christopher Nolan");
        movie.setDuration(169);
        movie.setGenres(List.of(Genre.Sci_fi));
        movie.setPrice(20);
        movie.setImage("InterstellarImage");

        ScreeningEntity futureScreening = new ScreeningEntity();
        futureScreening.setTime(LocalDateTime.now().plusDays(1));
        futureScreening.setMovie(movie);

        movie.setScreenings(List.of(futureScreening));
        movieRepository.save(movie);

        List<ScreeningDto> screenings = movieService.getMovieScreenings(movie.getId());

        assertEquals(1, screenings.size());
        assertTrue(screenings.stream().anyMatch(screening -> screening.getTime().isEqual(futureScreening.getTime())));
    }
}
