package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ExchangeService {
    Page<ExchangeListVO> findExchangeList(Map<String, Object> paramMap, Pageable page);

    Page<ExchangeListVO> searchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page);

    ExchangeDetailVO findExchangeDetailBy(Integer exchangeCode);

    Page<ExchangeHistoryVO> findExchangeHistoryList(Map<String, Object> paramMap, Pageable page);

    Page<ExchangeHistoryVO> searchExchangeHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page);

    ExchangeHistoryDetailVO findExchangeHistoryDetailBy(Integer exchangeStockHistoryCode);

    Page<FranExchangeListVO> findFranExchangeList(int franchiseCode, Map<String, Object> paramMap, Pageable page);

    FranExchangeDetailVO franFranExchangeDetailBy(int exchangeCode);

    List<FranExchangeStatusVO> findFranExchangeStatusBy(int exchangeCode);
}
