package com.zavrsni.Cinema.persistence.repository;

import com.zavrsni.Cinema.persistence.entity.ScreeningSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningSeatRepository extends JpaRepository<ScreeningSeatEntity, Long> {
}
