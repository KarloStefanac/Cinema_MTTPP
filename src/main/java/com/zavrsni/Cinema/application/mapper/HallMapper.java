package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.rest_api.dto.HallDto;
import com.zavrsni.Cinema.persistence.entity.HallEntity;
import com.zavrsni.Cinema.rest_api.request.HallRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface HallMapper {

    HallEntity mapToHall(HallRequest hallRequest);
    HallEntity mapToHall(HallDto hallDto);

    HallDto mapToHallDto(HallEntity hall);

    List<HallDto> mapToHallDto(List<HallEntity> hallEntities);
}
