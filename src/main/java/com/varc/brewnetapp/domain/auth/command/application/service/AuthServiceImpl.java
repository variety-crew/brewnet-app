package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.ChangePwRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.RoleType;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity.MemberRole;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity.Role;
import com.varc.brewnetapp.domain.auth.command.domain.repository.MemberRoleRepository;
import com.varc.brewnetapp.domain.auth.command.domain.repository.RoleRepository;
import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.Franchise;
import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.FranchiseMember;
import com.varc.brewnetapp.domain.franchise.command.domain.repository.FranchiseMemberRepository;
import com.varc.brewnetapp.domain.franchise.command.domain.repository.FranchiseRepository;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Position;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.PositionRepository;
import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.security.service.RefreshTokenService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RefreshTokenService refreshTokenService;
    private final PositionRepository positionRepository;
    private final FranchiseRepository franchiseRepository;
    private final FranchiseMemberRepository franchiseMemberRepository;
    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public AuthServiceImpl(
            MemberRepository memberRepository,
            MemberRoleRepository memberRoleRepository,
            ModelMapper modelMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RefreshTokenService refreshTokenService,
            PositionRepository positionRepository,
            FranchiseRepository franchiseRepository,
            FranchiseMemberRepository franchiseMemberRepository,
            RoleRepository roleRepository
    ) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.positionRepository = positionRepository;
        this.franchiseRepository = franchiseRepository;
        this.franchiseMemberRepository = franchiseMemberRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Member existMember = memberRepository.findById(signUpRequestDto.getId()).orElse(null);

        if(existMember != null)
            throw new DuplicateException("로그인 아이디가 이미 존재합니다");

        signUpRequestDto.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        Member member = modelMapper.map(signUpRequestDto, Member.class);
        member.setCreatedAt(LocalDateTime.now());
        member.setActive(true);

        if(signUpRequestDto.getPositionName() != null && signUpRequestDto.getFranchiseName() != null)
            throw new InvalidDataException("회원가입 시, 가맹점과 직급이 한꺼번에 설정될 수 없습니다");
        else if(signUpRequestDto.getPositionName() != null){
            member.setPositionCode(positionRepository.findByName
                (PositionName.valueOf(signUpRequestDto.getPositionName())).orElse(null)
                .getPositionCode());

            memberRepository.save(member);
        }
        else if(signUpRequestDto.getFranchiseName() != null){
            memberRepository.save(member);

            Franchise franchise = franchiseRepository.findByFranchiseName(signUpRequestDto.getFranchiseName()).orElse(null);

            if(franchise == null)
                throw new InvalidDataException("잘못된 가맹점 이름을 입력했습니다");

            FranchiseMember franchiseMember = FranchiseMember.builder()
                                                             .memberCode(member.getMemberCode())
                                                             .franchiseCode(franchise.getFranchiseCode())
                                                             .createdAt(LocalDateTime.now())
                                                             .active(true)
                                                             .build();

            franchiseMemberRepository.save(franchiseMember);

            Role role = roleRepository.findByRole(RoleType.ROLE_FRANCHISE);

            MemberRole memberRole = MemberRole.builder()
                .memberCode(member.getMemberCode())
                .roleCode(role.getRoleCode())
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

            memberRoleRepository.save(memberRole);
        }

    }

    @Override
    @Transactional
    public void logout(String loginId) {
        refreshTokenService.deleteRefreshToken(loginId);
    }

    @Override
    @Transactional
    public boolean changePassword(ChangePwRequestDTO changePwRequestDTO) {
        Member member = memberRepository.findById(changePwRequestDTO.getLoginId()).orElse(null);

        if(member == null)
            throw new InvalidDataException("비밀번호를 변경하려는 사용자가 존재하지 않습니다");

        String bcryptPw = bCryptPasswordEncoder.encode(changePwRequestDTO.getPassword());
        member.setPassword(bcryptPw);
        return memberRepository.save(member).getPassword().equals(bcryptPw);
    }

}
