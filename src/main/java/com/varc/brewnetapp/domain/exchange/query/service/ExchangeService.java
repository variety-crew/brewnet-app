package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ExchangeService {
    Page<ExchangeListResVO> findExchangeList(Map<String, Object> paramMap, Pageable page);
    Page<ExchangeListResVO> searchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page);
    ExchangeDetailResVO findExchangeDetailBy(Integer exchangeCode);
    Page<ExchangeHistoryResVO> findExchangeHistoryList(Map<String, Object> paramMap, Pageable page);
    Page<ExchangeHistoryResVO> searchExchangeHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page);
    ExchangeHistoryDetailResVO findExchangeHistoryDetailBy(Integer exchangeStockHistoryCode);
}
