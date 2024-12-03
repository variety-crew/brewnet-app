package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.ApprovalStatus;
import com.varc.brewnetapp.domain.member.query.dto.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    Page<MemberDTO> findMemberList(Pageable page, String search, String sort);

    MemberDTO findMember(String accessToken);

    Page<OrderPrintDTO> findSealHistory(Pageable page, String startDate, String endDate, String sort);

    FranchiseDTO getFranchiseInfoByLoginId(String loginId);

    Page<ApprovalDTO> findMyDraft(Pageable page, String dateSort, String approval, String startDate, String endDate, String accessToken);

    Page<ApprovalDTO> findMyApproval(Pageable page, String dateSort, String approval, String startDate, String endDate, String accessToken);

    MemberDTO getMemberByLoginId(String loginId);

    MemberDTO findMemberByHqMember(Integer memberCode);
}
