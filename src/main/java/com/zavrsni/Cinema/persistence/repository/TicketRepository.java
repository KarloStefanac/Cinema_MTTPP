package com.zavrsni.Cinema.persistence.repository;

import com.zavrsni.Cinema.persistence.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
