package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReturningService {
    Page<ReturningListVO> findReturningList(Pageable page);

    List<ReturningListVO> findAllReturningList();

    Page<ReturningListVO> findRequestedReturningList(Pageable page);

    Page<ReturningListVO> searchReturningList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page);

    ReturningDetailVO findReturningDetailBy(Integer returningCode);

    Page<FranReturningListVO> findFranReturningList(String loginId, Pageable page);

    Page<FranReturningListVO> searchFranReturningList(String loginId, String searchFilter, String searchWord, String startDate, String endDate, Pageable page);

    FranReturningDetailVO findFranReturningDetailBy(String loginId, int returningCode);

    boolean isValidReturningByFranchise(String loginId, int orderCode);

    List<Integer> findFranAvailableReturningBy(String loginId);

    List<FranReturningStatusVO> findFranReturningCodeStatusBy(String loginId, Integer returningCode);

    ReturningStatus findReturningLatestStatus(int returningCode);

    List<FranReturningItemVO> findFranAvailableReturningItemBy(String loginId, int orderCode);

    boolean isValidOrderByFranchise(String loginId, int orderCode);

    Page<ReturningHistoryVO> findReturningHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page);

    ReturningHistoryDetailVO findReturningHistoryDetailBy(Integer returningStockHistoryCode);

    Page<RefundHistoryVO> findRefundHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page);

    RefundHistoryDetailVO findRefundHistoryDetailBy(Integer returningRefundHistoryCode);

}
