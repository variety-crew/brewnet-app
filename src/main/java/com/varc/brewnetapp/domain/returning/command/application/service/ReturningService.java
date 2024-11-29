package com.varc.brewnetapp.domain.returning.command.application.service;

import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.Returning;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningDrafterApproveReqVO;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningManagerApproveReqVO;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningReqVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReturningService {
    Integer franCreateReturning(String loginId, ReturningReqVO returningReqVO, List<MultipartFile> returningImageList);

    void franCancelReturning(String loginId, Integer returningCode);

    void drafterReturning(String loginId, int returningCode, ReturningDrafterApproveReqVO returningApproveReqVO);

    void managerReturning(String loginId, int returningCode, ReturningManagerApproveReqVO returningApproveReqVO);

    void drafterApproveReturning(ReturningDrafterApproveReqVO returningApproveReqVO, Returning returning, Member member);

    void drafterRejectReturning(ReturningDrafterApproveReqVO returningApproveReqVO, Returning returning, Member member);

    void saveReturningApprover(Integer member, Returning returning, Approval approval, String createdAt, String comment);

    void saveReturningStatusHistory(ReturningStatus status, Returning returning);

    void completeStock(String loginId, int returningStockHistoryCode);

    void completeRefund(String loginId, int returningRefundHistoryCode);
}
