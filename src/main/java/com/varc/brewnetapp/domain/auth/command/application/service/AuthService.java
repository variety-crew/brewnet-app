package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;

public interface AuthService {
    void signUp(SignUpRequestDto signupRequestDto);
}
