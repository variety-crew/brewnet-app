package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.security.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "commandMemberService")
public class MemberServiceImpl implements MemberService {

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public MemberServiceImpl(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void logout(String loginId) {
        log.info("성공");
        refreshTokenService.deleteRefreshToken(loginId);
    }
}
