package com.zavrsni.Cinema.service;

import com.zavrsni.Cinema.application.mapper.MovieMapper;
import com.zavrsni.Cinema.application.mapper.ScreeningMapper;
import com.zavrsni.Cinema.application.service.impl.MovieServiceImpl;
import com.zavrsni.Cinema.persistence.entity.MovieEntity;
import com.zavrsni.Cinema.persistence.entity.ScreeningEntity;
import com.zavrsni.Cinema.persistence.repository.MovieRepository;
import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MovieServiceUTest {
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private ScreeningMapper screeningMapper;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMovie() {
        MovieRequest movieRequest = new MovieRequest();
        MovieEntity movieEntity = new MovieEntity();

        when(movieMapper.mapToMovie(movieRequest)).thenReturn(movieEntity);

        movieService.addMovie(movieRequest);

        verify(movieRepository, times(1)).save(movieEntity);
    }

    @Test
    void testGetAllMovies(){
        List<MovieEntity> movieEntities = List.of(new MovieEntity(), new MovieEntity());
        List<MovieDto> movieDtos = List.of(new MovieDto(), new MovieDto());

        when(movieRepository.findAll()).thenReturn(movieEntities);
        when(movieMapper.mapToMovieDto(any(MovieEntity.class))).thenReturn(new MovieDto());

        List<MovieDto> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findAll();
        verify(movieMapper, times(2)).mapToMovieDto(any(MovieEntity.class));
    }

    @Test
    void testGetMovieScreenings() {
        Long movieId = 1L;
        MovieEntity movieEntity = new MovieEntity();
        List<ScreeningEntity> allScreenings = new ArrayList<>();
        ScreeningEntity futureScreening = new ScreeningEntity();
        futureScreening.setTime(LocalDateTime.now().plusDays(1));  // Future screening
        allScreenings.add(futureScreening);

        movieEntity.setScreenings(allScreenings);

        when(movieRepository.findMovieById(movieId)).thenReturn(movieEntity);
        when(screeningMapper.mapToScreeningDtos(anyList()))
                .thenReturn(List.of(new ScreeningDto()));

        List<ScreeningDto> result = movieService.getMovieScreenings(movieId);

        assertEquals(1, result.size());
        verify(movieRepository, times(1)).findMovieById(movieId);
        verify(screeningMapper, times(1)).mapToScreeningDtos(anyList());
    }

    @Test
    void testGetMovieScreenings_NoFutureScreenings() {
        Long movieId = 1L;
        MovieEntity movieEntity = new MovieEntity();
        ScreeningEntity pastScreening = new ScreeningEntity();
        pastScreening.setTime(LocalDateTime.now().minusDays(1));  // Past screening
        movieEntity.setScreenings(List.of(pastScreening));

        when(movieRepository.findMovieById(movieId)).thenReturn(movieEntity);
        when(screeningMapper.mapToScreeningDtos(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<ScreeningDto> result = movieService.getMovieScreenings(movieId);

        assertTrue(result.isEmpty());
        verify(movieRepository, times(1)).findMovieById(movieId);
        verify(screeningMapper, times(1)).mapToScreeningDtos(Collections.emptyList());
    }
}
