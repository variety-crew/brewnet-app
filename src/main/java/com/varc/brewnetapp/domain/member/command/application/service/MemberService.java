package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.domain.member.command.application.dto.ChangeMemberRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.CheckNumDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.CheckPwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.LoginIdRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    boolean changePassword(ChangePwRequestDTO changePwRequestDTO);

    void deleteMember(String accessToken, LoginIdRequestDTO loginIdRequestDTO);

    void changeMember(String accessToken, ChangeMemberRequestDTO changeMemberRequestDTO);

    String checkPassword(String accessToken, CheckPwRequestDTO checkPasswordRequestDTO);

    void changeMyPassword(String accessToken, CheckPwRequestDTO checkPasswordRequestDTO);

    void createMySignature(String accessToken, MultipartFile signatureImg, CheckNumDTO checkNumDTO);

    void changeMySignature(String accessToken, MultipartFile signatureImg, CheckNumDTO checkNumDTO);

    void deleteMySignature(String accessToken, CheckNumDTO checkNumDTO);
}
