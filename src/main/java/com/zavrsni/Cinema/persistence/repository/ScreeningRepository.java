package com.zavrsni.Cinema.persistence.repository;

import com.zavrsni.Cinema.persistence.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<ScreeningEntity, Long> {
}
