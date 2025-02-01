package com.zavrsni.Cinema.application.mapper;

import com.zavrsni.Cinema.persistence.entity.UserEntity;
import com.zavrsni.Cinema.rest_api.dto.UserDto;
import com.zavrsni.Cinema.rest_api.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    UserEntity mapToUser(UserRequest userRequest);
    UserDto maptoUserDto(UserEntity userEntity);
}
