package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.domain.repository.MemberAuthRepository;
import com.varc.brewnetapp.domain.auth.command.domain.repository.RoleAuthRepository;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.Member;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.MemberRole;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberAuthRepository memberAuthRepository;
    private final RoleAuthRepository roleAuthRepository;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(
            MemberAuthRepository memberAuthRepository,
            RoleAuthRepository roleAuthRepository,
            ModelMapper modelMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.memberAuthRepository = memberAuthRepository;
        this.roleAuthRepository = roleAuthRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        signUpRequestDto.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));

        Member member = memberAuthRepository.save(modelMapper.map(signUpRequestDto, Member.class));

        MemberRole memberRole = new MemberRole();
        memberRole.setMemberCode(member.getMemberCode());
        memberRole.setRoleCode(1);
        roleAuthRepository.save(memberRole);

    }

}
