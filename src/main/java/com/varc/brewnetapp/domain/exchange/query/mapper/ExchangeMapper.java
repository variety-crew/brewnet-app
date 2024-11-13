package com.varc.brewnetapp.domain.exchange.query.mapper;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeItemVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExchangeMapper {
    int selectExchangeListCnt(Map<String, Object> paramMap);
    List<ExchangeListVO> selectExchangeList(Map<String, Object> paramMap);
    ExchangeDetailVO selectExchangeDetailBy(int exchangeCode);
    List<ExchangeItemVO> selectExchangeDetailItemBy(int exchangeCode);
}
