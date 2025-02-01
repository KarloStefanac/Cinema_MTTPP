package com.zavrsni.Cinema.application.service.impl;

import com.zavrsni.Cinema.application.mapper.TicketMapper;
import com.zavrsni.Cinema.application.service.TicketService;
import com.zavrsni.Cinema.persistence.entity.TicketEntity;
import com.zavrsni.Cinema.persistence.entity.UserEntity;
import com.zavrsni.Cinema.persistence.repository.ScreeningRepository;
import com.zavrsni.Cinema.persistence.repository.SeatRepository;
import com.zavrsni.Cinema.persistence.repository.TicketRepository;
import com.zavrsni.Cinema.persistence.repository.UserRepository;
import com.zavrsni.Cinema.rest_api.dto.TicketDto;
import com.zavrsni.Cinema.rest_api.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final ScreeningRepository screeningRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final TicketMapper ticketMapper;

    public TicketServiceImpl(ScreeningRepository screeningRepository, TicketRepository ticketRepository, UserRepository userRepository, SeatRepository seatRepository, TicketMapper ticketMapper) {
        this.screeningRepository = screeningRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public void addTickets(List<Long> seatIds, Long screeningId, String username) {
        for (Long seat : seatIds){
            TicketEntity ticket = new TicketEntity();
            ticket.setScreening(screeningRepository.findById(screeningId).orElseThrow(RuntimeException::new));
            ticket.setUser(userRepository.findByUsername(username).orElseThrow(RuntimeException::new));
            ticket.setSeat(seatRepository.findById(seat).orElseThrow(RuntimeException::new));
            ticketRepository.save(ticket);
        }
    }

    @Override
    public List<TicketDto> getUserTickets(UserDto userDto) {
        UserEntity user = userRepository.findById(userDto.getId()).orElseThrow(RuntimeException::new);
        List<TicketEntity> tickets = user.getTickets();

        return tickets.stream().map(ticketMapper::mapToTicketDto).toList();
    }
}
