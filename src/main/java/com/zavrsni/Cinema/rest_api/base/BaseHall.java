package com.zavrsni.Cinema.rest_api.base;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseHall {

    @NotEmpty(message = "Name is required")
    @Size(max = 255)
    private String name;

    @NotEmpty(message = "Hall capacity is required")
    private int capacity;
}
