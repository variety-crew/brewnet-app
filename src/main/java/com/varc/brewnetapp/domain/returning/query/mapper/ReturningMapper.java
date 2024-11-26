package com.varc.brewnetapp.domain.returning.query.mapper;

import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningListVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReturningMapper {
    int selectReturningListCnt();

    List<ReturningListVO> selectReturningList(long offset, long pageSize);

    List<ReturningListVO> selectAllReturningList();

    int selectRequestedReturningListCnt();

    List<ReturningListVO> selectRequestedReturningList(long offset, long pageSize);

    int selectSearchReturningListCnt(String searchFilter, String searchWord, String startDate, String endDate);

    List<ReturningListVO> selectSearchReturningList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    ReturningDetailVO selectReturningDetailBy(int returningCode);

    int selectFranReturningListCnt(String loginId);

    List<FranReturningListVO> selectFranReturningList(String loginId, long offset, long pageSize);

    int selectSearchFranReturningListCnt(String loginId, String searchFilter, String searchWord, String startDate, String endDate);

    List<FranReturningListVO> selectSearchFranReturningList(String loginId, String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);
}
