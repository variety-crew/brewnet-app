package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.common.TelNumberUtil;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.RoleType;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity.MemberRole;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity.Role;
import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.Franchise;
import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.FranchiseMember;
import com.varc.brewnetapp.domain.franchise.command.domain.repository.FranchiseMemberRepository;
import com.varc.brewnetapp.domain.franchise.command.domain.repository.FranchiseRepository;
import com.varc.brewnetapp.domain.member.command.application.dto.ChangeMemberRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.member.command.application.dto.LoginIdRequestDTO;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.PositionRepository;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final PositionRepository positionRepository;
    private final FranchiseRepository franchiseRepository;
    private final FranchiseMemberRepository franchiseMemberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberServiceImpl(
        MemberRepository memberRepository,
        PositionRepository positionRepository,
        FranchiseRepository franchiseRepository,
        FranchiseMemberRepository franchiseMemberRepository,
        BCryptPasswordEncoder bCryptPasswordEncoder,
        JwtUtil jwtUtil,
        ModelMapper modelMapper
    ) {
        this.memberRepository = memberRepository;
        this.positionRepository = positionRepository;
        this.franchiseRepository = franchiseRepository;
        this.franchiseMemberRepository = franchiseMemberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
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

    @Override
    @Transactional
    public void changeMember(String accessToken, ChangeMemberRequestDTO changeMemberRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            Member member = memberRepository.findById(changeMemberRequestDTO.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("변경하려는 회원이 없습니다"));

            if(changeMemberRequestDTO.getContact() != null)
                member.setContact(TelNumberUtil.formatTelNumber(changeMemberRequestDTO.getContact()));

            if(changeMemberRequestDTO.getName() != null)
                member.setName(changeMemberRequestDTO.getName());

            if(changeMemberRequestDTO.getEmail() != null)
                member.setEmail(changeMemberRequestDTO.getEmail());

            if(changeMemberRequestDTO.getPassword() != null)
                member.setPassword(bCryptPasswordEncoder.encode(changeMemberRequestDTO.getPassword()));

            if(changeMemberRequestDTO.getPositionName() != null && changeMemberRequestDTO.getFranchiseName() != null)
                throw new InvalidDataException("회원가입 시, 가맹점과 직급이 한꺼번에 설정될 수 없습니다");
            else if(changeMemberRequestDTO.getPositionName() != null){
                if(changeMemberRequestDTO.getPositionName().equals("사원"))
                    changeMemberRequestDTO.setPositionName("STAFF");
                else if(changeMemberRequestDTO.getPositionName().equals("대리"))
                    changeMemberRequestDTO.setPositionName("ASSISTANT_MANAGER");
                else if(changeMemberRequestDTO.getPositionName().equals("과장"))
                    changeMemberRequestDTO.setPositionName("MANAGER");
                else if(changeMemberRequestDTO.getPositionName().equals("대표이사"))
                    changeMemberRequestDTO.setPositionName("CEO");

                member.setPositionCode(positionRepository.findByName
                        (PositionName.valueOf(changeMemberRequestDTO.getPositionName()))
                    .orElseThrow(() -> new InvalidDataException("직급이 없습니다"))
                    .getPositionCode());

                memberRepository.save(member);
            } else if(changeMemberRequestDTO.getFranchiseName() != null){
                memberRepository.save(member);
                log.info("변경 회원 코드 : " + member.getMemberCode());

                Franchise franchise = franchiseRepository.findByFranchiseName(changeMemberRequestDTO.getFranchiseName())
                    .orElseThrow(() -> new InvalidDataException("잘못된 가맹점 이름을 입력했습니다"));

                FranchiseMember franchiseMember = franchiseMemberRepository.findByMemberCode(member.getMemberCode())
                    .orElseThrow(() -> new InvalidDataException("회원이 가맹점 유저가 아닙니다"));
                franchiseMember.setFranchiseCode(franchise.getFranchiseCode());

                franchiseMemberRepository.save(franchiseMember);
            }

        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");

    }
}
