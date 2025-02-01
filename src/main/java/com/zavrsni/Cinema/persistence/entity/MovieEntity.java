package com.zavrsni.Cinema.persistence.entity;

import com.zavrsni.Cinema.persistence.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MovieEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_movie")))
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;

    @Column(name = "director")
    private String director;

    @Column(name = "duration")
    private int duration;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScreeningEntity> screenings;

}

