package com.zavrsni.Cinema.application.service.impl;

import com.zavrsni.Cinema.application.mapper.ScreeningMapper;
import com.zavrsni.Cinema.application.service.ScreeningService;
import com.zavrsni.Cinema.persistence.entity.*;
import com.zavrsni.Cinema.persistence.repository.*;
import com.zavrsni.Cinema.rest_api.dto.ScreeningDto;
import com.zavrsni.Cinema.rest_api.request.ScreeningRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper screeningMapper;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;
    private final ScreeningSeatRepository screeningSeatRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, ScreeningMapper screeningMapper, MovieRepository movieRepository, HallRepository hallRepository, SeatRepository seatRepository, ScreeningSeatRepository screeningSeatRepository) {
        this.screeningRepository = screeningRepository;
        this.screeningMapper = screeningMapper;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
        this.screeningSeatRepository = screeningSeatRepository;
    }

    @Override
    public void addScreening(ScreeningRequest screeningRequest, Long movieId, Long hallId) {
        MovieEntity movieEntity = movieRepository.findMovieById(movieId);
        HallEntity hallEntity = hallRepository.findById(hallId).orElseThrow(RuntimeException::new);
        ScreeningEntity screeningEntity = screeningMapper.mapToScreening(screeningRequest);
        screeningEntity.setMovie(movieEntity);
        screeningEntity.setHall(hallEntity);
        screeningRepository.save(screeningEntity);
        movieEntity.getScreenings().add(screeningEntity);
        movieRepository.save(movieEntity);
    }

    @Override
    public ScreeningDto getScreening(Long screeningId) {
        return screeningMapper.mapToScreeningDto(screeningRepository.findById(screeningId).orElseThrow(RuntimeException::new));
    }

    @Override
    public List<ScreeningDto> getAllScreenings() {
        List<ScreeningEntity> screeningEntities = screeningRepository.findAll();
        return screeningEntities.stream().map(screeningMapper::mapToScreeningDto).toList();
    }

    @Override
    public List<Integer> getSeats(Long id) {
        ScreeningEntity screening = screeningRepository.findById(id).orElseThrow(RuntimeException::new);
        List<ScreeningSeatEntity> screeningSeats = screening.getScreeningSeats();
        List<Integer> seats = new ArrayList<>();

        screeningSeats.forEach((seat) -> seats.add(Math.toIntExact(seat.getSeat().getId())));
        return seats;
    }

    @Override
    public ScreeningDto addReservation(Long screeningId, List<Long> seatIds) {
        ScreeningEntity screening = screeningRepository.findById(screeningId).orElseThrow(RuntimeException::new);
        List<SeatEntity> seats = seatRepository.findAllById(seatIds);

        for (SeatEntity seat : seats){
            ScreeningSeatEntity screeningSeat = new ScreeningSeatEntity();

            screeningSeat.setScreening(screening);
            screeningSeat.setSeat(seat);
            screeningSeatRepository.save(screeningSeat);

        }

        return screeningMapper.mapToScreeningDto(screening);
    }

    @Override
    public Double getTotalPrice(Long count, Double price) {
        return count*price;
    }
}
