package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.persistence.entity.ScreeningEntity;
import com.zavrsni.Cinema.rest_api.request.ScreeningRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ScreeningMapper {
    ScreeningEntity mapToScreening(ScreeningDto screeningDto);

    @Mapping(source="hallDto", target = "hall")
    ScreeningEntity mapToScreening(ScreeningRequest screeningRequest);

    @Mapping(source = "movie", target = "movieDto")
    @Mapping(source = "hall", target = "hallDto")
    ScreeningDto mapToScreeningDto(ScreeningEntity screening);

    List<ScreeningDto> mapToScreeningDtos(List<ScreeningEntity> screenings);

}
