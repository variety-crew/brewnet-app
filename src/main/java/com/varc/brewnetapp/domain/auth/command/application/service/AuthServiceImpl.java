package com.varc.brewnetapp.domain.auth.command.application.service;

import com.varc.brewnetapp.domain.auth.command.application.dto.GrantAuthRequestDTO;
import com.varc.brewnetapp.domain.auth.command.application.dto.SignUpRequestDto;
import com.varc.brewnetapp.domain.auth.command.domain.aggregate.MemberRolePK;
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
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.PositionRepository;
import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import com.varc.brewnetapp.security.service.RefreshTokenService;
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
    private final JwtUtil jwtUtil;


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
            RoleRepository roleRepository,
            JwtUtil jwtUtil
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
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Member existId = memberRepository.findById(signUpRequestDto.getId()).orElse(null);
        if(existId != null)
            throw new DuplicateException("로그인 아이디가 이미 존재합니다");

        Member existEmail = memberRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if(existEmail != null)
            throw new DuplicateException("이메일이 이미 존재합니다");

        if (signUpRequestDto.getContact().length() != 11)
            throw new IllegalArgumentException("전화번호는 11자리여야 합니다.");

        signUpRequestDto.setContact(signUpRequestDto.getContact().substring(0, 3)
            + "-" + signUpRequestDto.getContact().substring(3, 7) + "-" + signUpRequestDto.getContact().substring(7));

        if(signUpRequestDto.getPositionName().equals("사원"))
            signUpRequestDto.setPositionName("STAFF");
        else if(signUpRequestDto.getPositionName().equals("대리"))
            signUpRequestDto.setPositionName("ASSISTANT_MANAGER");
        else if(signUpRequestDto.getPositionName().equals("과장"))
            signUpRequestDto.setPositionName("MANAGER");
        else if(signUpRequestDto.getPositionName().equals("대표"))
            signUpRequestDto.setPositionName("CEO");


        signUpRequestDto.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        Member member = modelMapper.map(signUpRequestDto, Member.class);
        member.setCreatedAt(LocalDateTime.now());
        member.setActive(true);

        if(signUpRequestDto.getPositionName() != null && signUpRequestDto.getFranchiseName() != null)
            throw new InvalidDataException("회원가입 시, 가맹점과 직급이 한꺼번에 설정될 수 없습니다");
        else if(signUpRequestDto.getPositionName() != null){
            member.setPositionCode(positionRepository.findByName
                (PositionName.valueOf(signUpRequestDto.getPositionName())).orElseThrow(() -> new InvalidDataException("직급이 없습니다"))
                .getPositionCode());

            memberRepository.save(member);
        }
        else if(signUpRequestDto.getFranchiseName() != null){
            memberRepository.save(member);

            Franchise franchise = franchiseRepository.findByFranchiseName(signUpRequestDto.getFranchiseName())
                .orElseThrow(() -> new InvalidDataException("잘못된 가맹점 이름을 입력했습니다"));

            FranchiseMember franchiseMember = FranchiseMember.builder()
                                                             .memberCode(member.getMemberCode())
                                                             .franchiseCode(franchise.getFranchiseCode())
                                                             .createdAt(LocalDateTime.now())
                                                             .active(true)
                                                             .build();

            franchiseMemberRepository.save(franchiseMember);

            Role role = roleRepository.findByRole(RoleType.ROLE_FRANCHISE).orElse(null);

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
    public void logout(String accessToken) {
        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));
        refreshTokenService.deleteRefreshToken(loginId);
    }


    @Override
    @Transactional
    public void grantAuth(String accessToken, GrantAuthRequestDTO grantAuthRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            Member member = memberRepository.findById(grantAuthRequestDTO.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("권한을 부여하려는 회원이 없습니다"));

            if(!member.getActive())
                throw new InvalidDataException("권한을 부여하려는 회원이 없습니다");

            Role role = roleRepository.findByRole(RoleType.valueOf(grantAuthRequestDTO.getAuthName()))
                .orElseThrow(() -> new InvalidDataException("잘못된 권한 값입니다"));

            MemberRolePK memberRolePK = new MemberRolePK(member.getMemberCode(), role.getRoleCode());
            MemberRole existMemberRole = memberRoleRepository.findById(memberRolePK).orElse(null);

            if(existMemberRole != null)
                throw new InvalidDataException("이미 해당 권한이 있는 사용자입니다");

            memberRoleRepository.save(MemberRole.builder()
                .memberCode(member.getMemberCode()).roleCode(role.getRoleCode())
                .createdAt(LocalDateTime.now()).active(true).build());
        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");

    }



}
