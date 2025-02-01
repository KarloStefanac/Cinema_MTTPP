package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.rest_api.dto.MovieDto;
import com.zavrsni.Cinema.persistence.entity.MovieEntity;
import com.zavrsni.Cinema.rest_api.request.MovieRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface MovieMapper {
    MovieEntity mapToMovie(MovieDto movieDto);

    MovieEntity mapToMovie(MovieRequest movieRequest);

    MovieDto mapToMovieDto(MovieEntity movie);
}
