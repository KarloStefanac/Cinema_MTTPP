package com.zavrsni.Cinema.rest_api.base;

import com.zavrsni.Cinema.persistence.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseMovie {
    private String name;
    private String director;
    private int duration;
    private List<Genre> genres;
    private double price;
    private String image;
}
