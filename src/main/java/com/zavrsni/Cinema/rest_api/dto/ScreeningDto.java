package com.zavrsni.Cinema.rest_api.dto;

import com.zavrsni.Cinema.rest_api.base.BaseScreening;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningDto extends BaseScreening {
    private Long id;
    private HallDto hallDto;
    private MovieDto movieDto;
}
