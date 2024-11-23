package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.member.query.dto.*;
import com.varc.brewnetapp.domain.member.query.mapper.MemberMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.MemberNotInFranchiseException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public Page<MemberDTO> findMemberList(Pageable page, String search) {
        // 페이징 정보 추가
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;


        // DB에서 교환 목록 조회
        List<MemberDTO> memberList = memberMapper.selectMemberList(offset, pageSize, search);

        if (memberList.isEmpty() || memberList.size() < 0)
            throw new EmptyDataException("조회하려는 회원 정보가 없습니다");

        // 전체 데이터 개수 조회
        int count = memberMapper.selectMemberListWhereSearchCnt(search);

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

    @Override
    @Transactional
    public Page<OrderPrintDTO> findSealHistory(Pageable page, String startDate, String endDate) {
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;

        if ((startDate == null || startDate.isEmpty()) ^ (endDate == null || endDate.isEmpty()))
            throw new InvalidDataException("시작일자와 종료일자는 모두 입력되거나, 둘 다 비어 있어야 합니다.");

        // DB에서 교환 목록 조회
        List<OrderPrintDTO> orderPrintList = memberMapper.selectOrderPrintList(offset, pageSize, startDate, endDate);

        if (orderPrintList.isEmpty() || orderPrintList.size() < 0)
            throw new EmptyDataException("조회하려는 법인 인감 사용 내역이 없습니다");

        int count = 0;

        if(startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty())
            count = memberMapper.selectOrderPrintListWhereDateCnt(startDate, endDate);
        else
            count = memberMapper.selectOrderPrintListCnt();

        return new PageImpl<>(orderPrintList, page, count);

    }

    public FranchiseDTO getFranchiseInfoByLoginId(String loginId) {
        FranchiseDTO franchiseDTO = memberMapper.getFranchiseInfoBy(loginId);
        if (franchiseDTO == null) {
            throw new MemberNotInFranchiseException("id: " + loginId + " is not a member of franchises");
        } else {
            return franchiseDTO;
        }
    }
}
