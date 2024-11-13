package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.domain.repository.MemberAuthRepository;
import com.varc.brewnetapp.domain.auth.command.domain.repository.RoleAuthRepository;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.Member;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.MemberRole;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.Position;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.PositionRepository;
import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.security.service.RefreshTokenService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final RoleAuthRepository roleAuthRepository;
    private final RefreshTokenService refreshTokenService;
    private final PositionRepository positionRepository;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public AuthServiceImpl(
            MemberRepository memberRepository,
            RoleAuthRepository roleAuthRepository,
            ModelMapper modelMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RefreshTokenService refreshTokenService,
            PositionRepository positionRepository
    ) {
        this.memberRepository = memberRepository;
        this.roleAuthRepository = roleAuthRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.positionRepository = positionRepository;
    }

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Member existMember = memberRepository.findById(signUpRequestDto.getId()).orElse(null);

        if(existMember != null)
            throw new DuplicateException("로그인 아이디가 이미 존재합니다");

        signUpRequestDto.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        log.info(signUpRequestDto.getPositionName().toString());
        Position position = positionRepository.findByName(signUpRequestDto.getPositionName()).orElse(null);
        log.info(position.getName().toString());
        Member member = modelMapper.map(signUpRequestDto, Member.class);
        member.setCreatedAt(LocalDateTime.now());
        member.setActive(true);
        member.setPosition(position);
        memberRepository.save(member);

    }

    @Override
    public void logout(String loginId) {
        refreshTokenService.deleteRefreshToken(loginId);
    }

}
