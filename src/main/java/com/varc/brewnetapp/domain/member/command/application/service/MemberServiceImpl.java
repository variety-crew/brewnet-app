package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.domain.member.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.LoginIdRequestDTO;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(value = "commandMemberService")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    @Transactional
    public boolean changePassword(ChangePwRequestDTO changePwRequestDTO) {
        Member member = memberRepository.findById(changePwRequestDTO.getLoginId())
            .orElseThrow(() -> new MemberNotFoundException("비밀번호를 변경하려는 사용자가 존재하지 않습니다"));

        if(!member.getActive())
            throw new InvalidDataException("비밀번호를 변경하려는 사용자가 존재하지 않습니다");

        String bcryptPw = bCryptPasswordEncoder.encode(changePwRequestDTO.getPassword());
        member.setPassword(bcryptPw);
        return memberRepository.save(member).getPassword().equals(bcryptPw);
    }

    @Override
    @Transactional
    public void deleteMember(String accessToken, LoginIdRequestDTO loginIdRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            Member member = memberRepository.findById(loginIdRequestDTO.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("삭제하려는 회원이 없습니다"));

            if(!member.getActive())
                throw new InvalidDataException("삭제하려는 회원이 없습니다");

            member.setActive(false);
            memberRepository.save(member);
        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");
    }
}
