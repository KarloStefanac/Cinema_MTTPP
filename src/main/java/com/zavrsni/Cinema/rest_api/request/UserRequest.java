package com.zavrsni.Cinema.rest_api.request;

import com.zavrsni.Cinema.rest_api.base.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest extends BaseUser {
    private String password;
    private boolean isStaff;
}
