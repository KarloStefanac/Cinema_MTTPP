package com.zavrsni.Cinema.persistence.entity;

import com.zavrsni.Cinema.persistence.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatEntity extends BaseEntity {

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScreeningSeatEntity> screeningSeats;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketEntity> tickets;
}
