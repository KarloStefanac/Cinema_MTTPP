package com.zavrsni.Cinema.rest_api.dto;

import com.zavrsni.Cinema.persistence.entity.Genre;
import com.zavrsni.Cinema.rest_api.base.BaseMovie;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto extends BaseMovie {

    private Long id;
}
