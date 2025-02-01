package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.persistence.entity.ScreeningSeatEntity;
import com.zavrsni.Cinema.rest_api.dto.ScreeningSeatDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ScreeningSeatMapper {
    @Mapping(source = "screening", target = "screeningDto")
    @Mapping(source = "seat", target = "seatDto")
    ScreeningSeatDto toScreeningSeatDto(ScreeningSeatEntity hallSeat);

    @Mapping(source = "screening", target = "screeningDto")
    @Mapping(source = "seat", target = "seatDto")
    List<ScreeningSeatDto> toScreeningSeatDtos(List<ScreeningSeatEntity> hallSeatEntities);
}
