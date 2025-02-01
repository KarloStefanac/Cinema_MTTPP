package com.zavrsni.Cinema.application.service;

import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.ScreeningRequest;

import java.util.List;

public interface ScreeningService {
    void addScreening(ScreeningRequest screeningRequest, Long movieId, Long hallId);

    ScreeningDto getScreening(Long screeningId);
    List<ScreeningDto> getAllScreenings();
    List<Integer> getSeats(Long id);
    ScreeningDto addReservation(Long screeningId, List<Long> seatIds);
    Double getTotalPrice(Long count, Double price);
}
