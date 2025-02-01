package com.zavrsni.Cinema.application.service;

import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;

import java.util.List;

public interface MovieService {
    /**
     * Save movie to the repository
     * @param movieRequest DTO Movie object
     */
    void addMovie(MovieRequest movieRequest);

    List<MovieDto> getAllMovies();

    MovieDto getMovie(Long id);

    List<ScreeningDto> getMovieScreenings(Long id);
}
