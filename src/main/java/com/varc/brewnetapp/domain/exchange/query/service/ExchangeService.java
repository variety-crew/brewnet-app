package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ExchangeService {
    Page<ExchangeListResponseVO> findExchangeList(Map<String, Object> paramMap, Pageable page);
    Page<ExchangeListResponseVO> searchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page);
    ExchangeDetailResponseVO findExchangeDetailBy(Integer exchangeCode);
    Page<ExchangeHistoryResponseVO> findExchangeHistoryList(Map<String, Object> paramMap, Pageable page);
    ExchangeHistoryDetailResponseVO findExchangeHistoryDetailBy(Integer exchangeStockHistoryCode);
}
