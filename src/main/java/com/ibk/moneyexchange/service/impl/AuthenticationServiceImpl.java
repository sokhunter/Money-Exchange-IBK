package com.ibk.moneyexchange.service.impl;

import com.ibk.moneyexchange.config.security.JwtProvider;
import com.ibk.moneyexchange.controller.dto.UserDto;
import com.ibk.moneyexchange.controller.handler.exceptions.UserAuthenticationException;
import com.ibk.moneyexchange.repository.database.UserRepository;
import com.ibk.moneyexchange.service.AuthenticationService;
import com.ibk.moneyexchange.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserDto userDto) {
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(MapperUtils.toUserEntity(userDto));
        } catch (Exception ex) {
            log.error("Error storing user", ex);
            throw new UserAuthenticationException(ex);
        }
    }

    @Override
    public String authenticateUser(UserDto userDto) {
        log.info("First Step: Authenticate user");
        try {
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(),
                            userDto.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (AuthenticationException ex) {
            log.error("Error into authenticationManager", ex);
            throw new UserAuthenticationException(ex);
        }
        log.info("Second Step: Generate token");
        return jwtProvider.generateToken(userDto.getUsername());
    }
}
