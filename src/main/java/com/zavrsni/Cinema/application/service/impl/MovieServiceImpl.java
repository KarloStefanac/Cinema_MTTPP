package com.zavrsni.Cinema.application.service.impl;

import com.zavrsni.Cinema.application.mapper.ScreeningMapper;
import com.zavrsni.Cinema.application.service.MovieService;
import com.zavrsni.Cinema.persistence.entity.ScreeningEntity;
import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import com.zavrsni.Cinema.persistence.entity.MovieEntity;
import com.zavrsni.Cinema.application.mapper.MovieMapper;
import com.zavrsni.Cinema.persistence.repository.MovieRepository;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ScreeningMapper screeningMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper, ScreeningMapper screeningMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.screeningMapper = screeningMapper;
    }

    @Override
    public void addMovie(MovieRequest movieRequest) {
        movieRepository.save(movieMapper.mapToMovie(movieRequest));
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<MovieEntity> movies = movieRepository.findAll();
        return movies.stream().map(movieMapper::mapToMovieDto).toList();
    }

    @Override
    public MovieDto getMovie(Long id) {
        return movieMapper.mapToMovieDto(movieRepository.findMovieById(id));
    }

    @Override
    public List<ScreeningDto> getMovieScreenings(Long id) {
        MovieEntity movie = movieRepository.findMovieById(id);
        List<ScreeningEntity> allScreenings = movie.getScreenings();
        List<ScreeningEntity> futureScreenings = new ArrayList<>();
        for (ScreeningEntity screening : allScreenings){
            if (LocalDateTime.now().isBefore(screening.getTime())){
                futureScreenings.add(screening);
            }
        }
        return screeningMapper.mapToScreeningDtos(futureScreenings);
    }
}
