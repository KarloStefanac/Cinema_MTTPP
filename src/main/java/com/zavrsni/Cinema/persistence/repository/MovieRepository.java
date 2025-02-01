package com.zavrsni.Cinema.persistence.repository;

import com.zavrsni.Cinema.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    MovieEntity findMovieById(Long id);

    boolean existsByName(String name);
}
