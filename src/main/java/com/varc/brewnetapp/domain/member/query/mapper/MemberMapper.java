package com.varc.brewnetapp.domain.member.query.mapper;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.ApprovalStatus;
import com.varc.brewnetapp.domain.member.query.dto.*;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    List<MemberDTO> selectMemberList(long offset, long pageSize, String search, String sort);

    CompanyDTO selectCompany();

    SealDTO selectCompanySeal();

    MemberDTO selectMember(String loginId);

    List<OrderPrintDTO> selectOrderPrintList(long offset, long pageSize, String startDate, String endDate);

    int selectOrderPrintListCnt();

    int selectMemberListWhereSearchCnt(String search);

    int selectOrderPrintListWhereDateCnt(String startDate, String endDate);

    FranchiseDTO getFranchiseInfoBy(String loginId);

    List<ApprovalDTO> selectDraftList(long pageSize, long offset, String dateSort, String approval, String startDate, String endDate, int memberCode);

    int selectDraftListCnt(long pageSize, long offset, String approval, String startDate, String endDate, int memberCode);

    List<ApprovalDTO> selectApprovalList(long pageSize, long offset, String dateSort, String approval, String startDate, String endDate, int memberCode);

    int selectApprovalListCnt(long pageSize, long offset, String approval, String startDate, String endDate, int memberCode);

    MemberDTO selectFranchiseMember(String loginId);
}
