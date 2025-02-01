package com.zavrsni.Cinema.rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreeningSeatDto {
    private Long id;
    private ScreeningDto screeningDto;
    private SeatDto seatDto;

}
