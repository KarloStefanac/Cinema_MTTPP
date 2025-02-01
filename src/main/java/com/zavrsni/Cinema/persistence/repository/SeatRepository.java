package com.zavrsni.Cinema.persistence.repository;

import com.zavrsni.Cinema.persistence.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    @Query("SELECT COUNT(s) > 0 FROM SeatEntity s")
    boolean existsData();

}
