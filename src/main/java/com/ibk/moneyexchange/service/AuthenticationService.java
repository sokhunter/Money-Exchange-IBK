package com.ibk.moneyexchange.service;

import com.ibk.moneyexchange.controller.dto.UserDto;

public interface AuthenticationService {
    void registerUser(UserDto userDto);
    String authenticateUser(UserDto userDto);
}
