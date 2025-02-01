package com.zavrsni.Cinema.application.service.impl;

import com.zavrsni.Cinema.application.mapper.TicketMapper;
import com.zavrsni.Cinema.application.mapper.UserMapper;
import com.zavrsni.Cinema.application.service.UserService;
import com.zavrsni.Cinema.persistence.entity.UserEntity;
import com.zavrsni.Cinema.persistence.repository.UserRepository;
import com.zavrsni.Cinema.rest_api.dto.UserDto;
import com.zavrsni.Cinema.rest_api.request.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private TicketMapper ticketMapper;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, TicketMapper ticketMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public UserDto addUser(UserRequest userRequest) {
        UserEntity user = userMapper.mapToUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRequest.isStaff()){
            user.setRole("USER,ADMIN");
        }else{
            user.setRole("USER");
        }
        return userMapper.maptoUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        return userMapper.maptoUserDto(userEntity);
    }
}
