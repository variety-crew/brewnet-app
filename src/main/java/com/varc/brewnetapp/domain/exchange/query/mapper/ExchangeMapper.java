package com.varc.brewnetapp.domain.exchange.query.mapper;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ExchangeMapper {
    int selectExchangeListCnt();

    List<ExchangeListVO> selectExchangeList(long offset, long pageSize);

    List<ExchangeListVO> selectSearchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    ExchangeDetailVO selectExchangeDetailBy(int exchangeCode);

    int selectExchangeHistoryListCnt();

    List<ExchangeHistoryVO> selectExchangeHistoryList(long offset, long pageSize);

    List<ExchangeHistoryVO> selectSearchExchangeHistoryList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    ExchangeHistoryDetailVO selectExchangeHistoryDetailBy(int exchangeStockHistoryCode);

    int selectFranExchangeListCnt(int franchiseCode);

    List<FranExchangeListVO> selectFranExchangeList(int franchiseCode, long offset, long pageSize);

    List<FranExchangeListVO> selectSearchFranExchangeList(int franchiseCode, String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    FranExchangeDetailVO selectFranExchangeDetailBy(int exchangeCode);

    List<FranExchangeStatusVO> selectFranExchangeStatusBy(int exchangeCode);

    Optional<ExchangeStatus> selectExchangeLatestStatusBy(int exchangeCode);

    List<ExchangeApproverVO> selectExchangeApproverBy(int exchangeCode);
}
