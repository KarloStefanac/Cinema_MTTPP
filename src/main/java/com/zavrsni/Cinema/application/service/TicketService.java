package com.zavrsni.Cinema.application.service;

import com.zavrsni.Cinema.rest_api.dto.TicketDto;
import com.zavrsni.Cinema.rest_api.dto.UserDto;

import java.util.List;

public interface TicketService {
    void addTickets(List<Long> seatIds, Long screeningId, String username);
    List<TicketDto> getUserTickets(UserDto userDto);
}
