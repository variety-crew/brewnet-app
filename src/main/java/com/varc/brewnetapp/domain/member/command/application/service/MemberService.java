package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.domain.member.command.application.dto.ChangeMemberRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.CheckPwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.LoginIdRequestDTO;

public interface MemberService {

    boolean changePassword(ChangePwRequestDTO changePwRequestDTO);

    void deleteMember(String accessToken, LoginIdRequestDTO loginIdRequestDTO);

    void changeMember(String accessToken, ChangeMemberRequestDTO changeMemberRequestDTO);

    void checkPassword(String accessToken, CheckPwRequestDTO checkPasswordRequestDTO);

    void changeMyPassword(String accessToken, CheckPwRequestDTO checkPasswordRequestDTO);
}
