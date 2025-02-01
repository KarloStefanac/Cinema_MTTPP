package com.zavrsni.Cinema.persistence.entity;

import com.zavrsni.Cinema.persistence.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "halls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HallEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name="capacity")
    private int capacity;

//    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ScreeningSeatEntity> hallSeats;

//    @OneToOne(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
//    private ScreeningEntity screening;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ScreeningEntity> screenings;
}
