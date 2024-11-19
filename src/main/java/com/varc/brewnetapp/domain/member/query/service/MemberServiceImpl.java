package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.member.query.dto.CompanyDTO;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import com.varc.brewnetapp.domain.member.query.dto.SealDTO;
import com.varc.brewnetapp.domain.member.query.mapper.MemberMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "queryMemberService")
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberServiceImpl
        (
            MemberMapper memberMapper,
            JwtUtil jwtUtil,
            ModelMapper modelMapper) {
        this.memberMapper = memberMapper;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Page<MemberDTO> findMemberList(Pageable page) {
        // 페이징 정보 추가
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = (pageNumber - 1) * pageSize;

        // DB에서 교환 목록 조회
        List<MemberDTO> memberList = memberMapper.selectMemberList(offset, pageSize);

        if (memberList.isEmpty() || memberList.size() < 0)
            throw new EmptyDataException("조회하려는 회원 정보가 없습니다");

        memberList.stream().forEach(member -> {
            if(member.getPositionName().equals("STAFF"))
                member.setPositionName("사원");
            else if(member.getPositionName().equals("ASSISTANT_MANAGER"))
                member.setPositionName("대리");
            else if(member.getPositionName().equals("MANAGER"))
                member.setPositionName("과장");
            else if(member.getPositionName().equals("CEO"))
                member.setPositionName("대표이사");
        });

        // 전체 데이터 개수 조회
        int count = memberMapper.selectMemberListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(memberList, page, count);
    }

    @Override
    @Transactional
    public MemberDTO findMember(String accessToken) {
        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));
        MemberDTO member = memberMapper.selectMember(loginId);

        if(member == null)
            throw new MemberNotFoundException("조회하려는 멤버 정보가 없습니다");

        return member;
        
    }


}
