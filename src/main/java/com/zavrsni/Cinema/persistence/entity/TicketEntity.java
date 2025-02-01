package com.zavrsni.Cinema.persistence.entity;

import com.zavrsni.Cinema.persistence.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="screening_id", foreignKey = @ForeignKey(name = "fk_ticket_screening"))
    private ScreeningEntity screening;

    @ManyToOne
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="fk_ticket_user"))
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="seat_id", foreignKey = @ForeignKey(name="fk_ticket_seat"))
    private SeatEntity seat;
}
