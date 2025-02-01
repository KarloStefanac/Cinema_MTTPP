package com.zavrsni.Cinema.persistence.entity;

import com.zavrsni.Cinema.persistence.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "screening_seat")
public class ScreeningSeatEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "fk_screening_seat"))
    private ScreeningEntity screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(name = "fk_seat_hall"))
    private SeatEntity seat;
}
