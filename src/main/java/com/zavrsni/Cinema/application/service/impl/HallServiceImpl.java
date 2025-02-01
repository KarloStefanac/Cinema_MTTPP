package com.zavrsni.Cinema.application.service.impl;

import com.zavrsni.Cinema.application.mapper.HallMapper;
import com.zavrsni.Cinema.application.mapper.ScreeningSeatMapper;
import com.zavrsni.Cinema.application.service.HallService;
import com.zavrsni.Cinema.persistence.entity.HallEntity;
import com.zavrsni.Cinema.persistence.entity.ScreeningSeatEntity;
import com.zavrsni.Cinema.persistence.entity.SeatEntity;
import com.zavrsni.Cinema.persistence.repository.ScreeningSeatRepository;
import com.zavrsni.Cinema.persistence.repository.SeatRepository;
import com.zavrsni.Cinema.rest_api.dto.HallDto;
import com.zavrsni.Cinema.persistence.repository.HallRepository;
import com.zavrsni.Cinema.rest_api.request.HallRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;
    private final HallMapper hallMapper;

    public HallServiceImpl(HallRepository hallRepository, HallMapper hallMapper) {
        this.hallRepository = hallRepository;
        this.hallMapper = hallMapper;
    }

    @Override
    public List<HallDto> getAllHalls() {
        return hallMapper.mapToHallDto(hallRepository.findAll());
    }

    @Override
    public HallDto getHallById(Long id) {
        return hallMapper.mapToHallDto(hallRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public void addHall(HallRequest hallRequest) {
        hallRepository.save(hallMapper.mapToHall(hallRequest));
    }

}
