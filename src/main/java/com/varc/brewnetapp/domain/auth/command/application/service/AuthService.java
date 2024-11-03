package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpResponseDto;

public interface AuthService {
    SignUpResponseDto signUp(SignUpRequestDto signupRequestDto);
}
