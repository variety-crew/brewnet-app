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
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "queryMemberService")
@Slf4j
@Primary
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl
        (
            MemberMapper memberMapper,
            JwtUtil jwtUtil,
            ModelMapper modelMapper,
            MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Page<MemberDTO> findMemberList(Pageable page, String search, String sort) {
        // 페이징 정보 추가
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;


        // DB에서 교환 목록 조회
        List<MemberDTO> memberList = memberMapper.selectMemberList(offset, pageSize, search, sort);

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
        Member member = memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("조회하려는 멤버 정보가 없습니다"));

        MemberDTO memberDTO = null;

        if(member.getPositionCode() != null)
            memberDTO = memberMapper.selectMember(loginId);
        else
            memberDTO = memberMapper.selectFranchiseMember(loginId);

        if(memberDTO == null)
            throw new MemberNotFoundException("조회하려는 멤버 정보가 없습니다");

        return memberDTO;
        
    }

    @Override
    @Transactional
    public MemberDTO findMemberByHqMember(Integer memberCode) {

        Member member = memberRepository.findById(memberCode)
            .orElseThrow(() -> new MemberNotFoundException("조회하려는 멤버 정보가 없습니다"));

        MemberDTO memberDTO = null;

        if(member.getPositionCode() != null)
            memberDTO = memberMapper.selectMemberByHqMember(memberCode);
        else{
            memberDTO = memberMapper.selectFranchiseMemberByHqMember(memberCode);

            if(memberDTO == null)
                memberDTO = memberMapper.selectDeliveryMemberByHqMEmber(memberCode);
        }
        
        if(memberDTO == null)
            throw new MemberNotFoundException("조회하려는 멤버 정보가 없습니다");

        return memberDTO;

    }

    @Override
    @Transactional
    public Page<OrderPrintDTO> findSealHistory(Pageable page, String startDate, String endDate, String sort) {
        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;

        if ((startDate == null || startDate.isEmpty()) ^ (endDate == null || endDate.isEmpty()))
            throw new InvalidDataException("시작일자와 종료일자는 모두 입력되거나, 둘 다 비어 있어야 합니다.");

        // DB에서 교환 목록 조회
        List<OrderPrintDTO> orderPrintList = memberMapper.selectOrderPrintList(offset, pageSize, startDate, endDate, sort);

        if (orderPrintList.isEmpty() || orderPrintList.size() < 0)
            throw new EmptyDataException("조회하려는 법인 인감 사용 내역이 없습니다");

        int count = 0;

        if(startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty())
            count = memberMapper.selectOrderPrintListWhereDateCnt(startDate, endDate);
        else
            count = memberMapper.selectOrderPrintListCnt();

        return new PageImpl<>(orderPrintList, page, count);

    }

    @Override
    @Transactional
    public FranchiseDTO getFranchiseInfoByLoginId(String loginId) {
        FranchiseDTO franchiseDTO = memberMapper.getFranchiseInfoBy(loginId);
        if (franchiseDTO == null) {
            throw new MemberNotInFranchiseException("id: " + loginId + " is not a member of franchises");
        } else {
            return franchiseDTO;
        }
    }

    @Override
    @Transactional
    public Page<ApprovalDTO> findMyDraft(Pageable page, String dateSort, String approval,
        String startDate, String endDate, String accessToken) {

        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;

        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));

        int memberCode = memberRepository.findById(loginId)
            .orElseThrow(() -> new MemberNotFoundException("토큰에 맞는회원 정보가 없습니다")).getMemberCode();

        List<ApprovalDTO> draftList = memberMapper.selectDraftList(pageSize, offset, dateSort, approval,
            startDate, endDate, memberCode);

        int count = memberMapper.selectDraftListCnt(pageSize, offset, approval,
            startDate, endDate, memberCode);

        return new PageImpl<>(draftList, page, count);
    }

    @Override
    @Transactional
    public Page<ApprovalDTO> findMyApproval(Pageable page, String dateSort, String approval,
        String startDate, String endDate, String accessToken) {

        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;

        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));

        int memberCode = memberRepository.findById(loginId)
            .orElseThrow(() -> new MemberNotFoundException("토큰에 맞는회원 정보가 없습니다")).getMemberCode();

        List<ApprovalDTO> approvalList = memberMapper.selectApprovalList(pageSize, offset, dateSort, approval,
            startDate, endDate, memberCode);

        int count = memberMapper.selectApprovalListCnt(pageSize, offset, approval,
            startDate, endDate, memberCode);

        return new PageImpl<>(approvalList, page, count);
    }

    @Override
    @Transactional
    public MemberDTO getMemberByLoginId(String loginId) {
        MemberDTO resultMemberDTO = memberMapper.selectMember(loginId);
        if (Objects.isNull(resultMemberDTO)) {
            throw new MemberNotFoundException("Member not found. loginId: " + loginId);
        } else {
            return resultMemberDTO;
        }
    }
}
