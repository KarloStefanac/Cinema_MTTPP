package com.zavrsni.Cinema.rest_api.dto;

import com.zavrsni.Cinema.rest_api.base.BaseUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseUser {
    private Long id;
    private String role;
}
