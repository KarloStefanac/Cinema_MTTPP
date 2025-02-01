package com.zavrsni.Cinema.rest_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HallDto {

    private Long id;

    private String name;

    private int capacity;
}
