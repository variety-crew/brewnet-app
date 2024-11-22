package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.GrantAuthRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;

public interface AuthService {
    void signUp(SignUpRequestDto signupRequestDto);

    void logout(String accessToken);

    void grantAuth(String accessToken, GrantAuthRequestDTO grantAuthRequestDTO);

}
