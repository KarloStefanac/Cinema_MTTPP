package com.zavrsni.Cinema.service;

import com.zavrsni.Cinema.application.mapper.MovieMapper;
import com.zavrsni.Cinema.application.mapper.ScreeningMapper;
import com.zavrsni.Cinema.application.service.impl.MovieServiceImpl;
import com.zavrsni.Cinema.persistence.entity.MovieEntity;
import com.zavrsni.Cinema.persistence.repository.MovieRepository;
import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieServiceUT {
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
    void testGetAllMovies(){
        List<MovieEntity> entities = List.of(new MovieEntity(), new MovieEntity());
        List<MovieDto> dtos = List.of(new MovieDto(), new MovieDto());

        when(movieRepository.findAll()).thenReturn(entities);
        when(movieMapper.mapToMovieDto(any(MovieEntity.class))).thenReturn(new MovieDto());

        // Act
        List<MovieDto> result = movieService.getAllMovies();

        // Assert
        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findAll();
    }
}
