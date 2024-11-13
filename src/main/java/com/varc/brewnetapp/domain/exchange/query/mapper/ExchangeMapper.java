package com.varc.brewnetapp.domain.exchange.query.mapper;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeHistoryVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExchangeMapper {
    int selectExchangeListCnt(Map<String, Object> paramMap);

    List<ExchangeListVO> selectExchangeList(Map<String, Object> paramMap);

    List<ExchangeListVO> selectSearchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, long offset, long pageSize);

    ExchangeDetailVO selectExchangeDetailBy(int exchangeCode);

    int selectExchangeHistoryListCnt(Map<String, Object> paramMap);

    List<ExchangeHistoryVO> selectExchangeHistoryList(Map<String, Object> paramMap);
}
