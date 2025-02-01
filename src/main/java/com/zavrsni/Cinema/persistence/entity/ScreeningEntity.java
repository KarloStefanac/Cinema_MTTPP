package com.zavrsni.Cinema.persistence.entity;

import com.zavrsni.Cinema.persistence.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "screenings")
public class ScreeningEntity extends BaseEntity {

//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Future
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name="hall_id", foreignKey = @ForeignKey(name = "fk_screening_hall"))
    private HallEntity hall;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScreeningSeatEntity> screeningSeats;

    @ManyToOne
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_screening_movie"))
    private MovieEntity movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketEntity> tickets;

}
