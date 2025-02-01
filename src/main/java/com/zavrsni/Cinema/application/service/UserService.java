package com.zavrsni.Cinema.application.service;

import com.zavrsni.Cinema.rest_api.dto.UserDto;
import com.zavrsni.Cinema.rest_api.request.UserRequest;

public interface UserService {
    UserDto addUser(UserRequest userRequest);
    UserDto getUser(String username);

}
