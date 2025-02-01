package com.zavrsni.Cinema.application.service;

import com.zavrsni.Cinema.persistence.entity.HallEntity;
import com.zavrsni.Cinema.rest_api.dto.HallDto;
import com.zavrsni.Cinema.rest_api.request.HallRequest;

import java.util.List;


public interface HallService {

    List<HallDto> getAllHalls();

    HallDto getHallById(Long id);

    void addHall(HallRequest hallRequest);

}
