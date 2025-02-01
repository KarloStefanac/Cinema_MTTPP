package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.persistence.entity.TicketEntity;
import com.zavrsni.Cinema.rest_api.dto.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface TicketMapper {
    TicketEntity mapToTicket(TicketDto ticketDto);
    @Mapping(source = "screening", target = "screeningDto")
    @Mapping(source = "screening.movie", target = "screeningDto.movieDto")
    @Mapping(source = "screening.hall", target = "screeningDto.hallDto")
    @Mapping(source = "seat", target = "seatDto")
    TicketDto mapToTicketDto(TicketEntity ticketEntity);
}
