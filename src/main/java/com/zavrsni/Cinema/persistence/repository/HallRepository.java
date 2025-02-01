package com.zavrsni.Cinema.persistence.repository;

import com.zavrsni.Cinema.persistence.entity.HallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<HallEntity, Long> {
    boolean existsByName(String name);
}
