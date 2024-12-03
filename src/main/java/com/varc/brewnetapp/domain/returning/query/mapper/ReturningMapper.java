package com.varc.brewnetapp.domain.returning.query.mapper;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReturningMapper {
    int selectReturningListCnt();

    List<ReturningListVO> selectReturningList(long offset, long pageSize);

    List<ReturningListVO> selectExcelReturningList(String searchFilter, String searchWord, String startDate, String endDate);

    int selectRequestedReturningListCnt();

    List<ReturningListVO> selectRequestedReturningList(long offset, long pageSize);

    int selectSearchReturningListCnt(String searchFilter, String searchWord, String startDate, String endDate);

    List<ReturningListVO> selectSearchReturningList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    ReturningDetailVO selectReturningDetailBy(int returningCode);

    int selectFranReturningListCnt(String loginId);

    List<FranReturningListVO> selectFranReturningList(String loginId, long offset, long pageSize);

    int selectSearchFranReturningListCnt(String loginId, String searchFilter, String searchWord, String startDate, String endDate);

    List<FranReturningListVO> selectSearchFranReturningList(String loginId, String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    FranReturningDetailVO selectFranReturningDetailBy(int returningCode);

    boolean selectValidReturningByFranchise(String loginId, int returningCode);

    List<FranReturningStatusVO> selectFranReturningStatusBy(Integer returningCode);

    Optional<ReturningStatus> selectReturningLatestStatusBy(int returningCode);

    List<Integer> selectAvailableReturningBy(String loginId);

    List<FranReturningItemVO> selectAvailableReturningItemBy(int orderCode);

    boolean selectValidOrderByFranchise(String loginId, int orderCode);

    int selectReturningHistoryListCnt(String searchFilter, String searchWord, String startDate, String endDate);

    List<ReturningHistoryVO> selectReturningHistoryList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    ReturningHistoryDetailVO selectReturningHistoryDetailBy(int returningStockHistoryCode);

    int selectRefundHistoryListCnt(String searchFilter, String searchWord, String startDate, String endDate);

    List<RefundHistoryVO> selectRefundHistoryList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    RefundHistoryDetailVO selectRefundHistoryDetailBy(int returningRefundHistoryCode);

    List<ReturningApproverVO> selectReturningApproverBy(int returningCode);
}
