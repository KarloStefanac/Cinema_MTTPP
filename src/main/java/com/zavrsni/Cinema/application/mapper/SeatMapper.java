package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.persistence.entity.SeatEntity;
import com.zavrsni.Cinema.rest_api.dto.SeatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface SeatMapper {
    SeatEntity mapToSeat(SeatDto seatDto);
    SeatDto mapToSeatDto(SeatEntity seat);
}
