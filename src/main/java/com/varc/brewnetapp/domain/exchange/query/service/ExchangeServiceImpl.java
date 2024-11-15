package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import com.varc.brewnetapp.domain.exchange.query.mapper.ExchangeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("ExchangeServiceQuery")
@Slf4j
public class ExchangeServiceImpl implements ExchangeService{

    private final ExchangeMapper exchangeMapper;

    @Autowired
    public ExchangeServiceImpl(ExchangeMapper exchangeMapper) {
        this.exchangeMapper = exchangeMapper;
    }

    @Override
    public Page<ExchangeListVO> findExchangeList(Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        paramMap.put("offset", page.getOffset());
        paramMap.put("pageSize", page.getPageSize());

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectExchangeList(paramMap);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeList, page, count);
    }

    @Override
    public Page<ExchangeListVO> searchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectSearchExchangeList(searchFilter, searchWord, startDate, endDate, offset, pageSize);


        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeList, page, count);
    }

    @Override
    public ExchangeDetailVO findExchangeDetailBy(Integer exchangeCode) {
        ExchangeDetailVO exchangeDetail = exchangeMapper.selectExchangeDetailBy(exchangeCode);
        return exchangeDetail;
    }

    @Override
    public Page<ExchangeHistoryVO> findExchangeHistoryList(Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        paramMap.put("offset", page.getOffset());
        paramMap.put("pageSize", page.getPageSize());

        // DB에서 교환 목록 조회
        List<ExchangeHistoryVO> exchangeHistoryList = exchangeMapper.selectExchangeHistoryList(paramMap);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeHistoryListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeHistoryList, page, count);
    }

    @Override
    public Page<ExchangeHistoryVO> searchExchangeHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ExchangeHistoryVO> exchangeHistoryList = exchangeMapper.selectSearchExchangeHistoryList (searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeHistoryListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeHistoryList, page, count);
    }

    @Override
    public ExchangeHistoryDetailVO findExchangeHistoryDetailBy(Integer exchangeStockHistoryCode) {
        ExchangeHistoryDetailVO exchangeHistoryDetail = exchangeMapper.selectExchangeHistoryDetailBy(exchangeStockHistoryCode);
        return exchangeHistoryDetail;
    }

    @Override
    public Page<FranExchangeListVO> findFranExchangeList(int franchiseCode, Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<FranExchangeListVO> franExchangeList = exchangeMapper.selectFranExchangeList(franchiseCode, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectFranExchangeListCnt(franchiseCode);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(franExchangeList, page, count);
    }

    @Override
    public FranExchangeDetailVO franFranExchangeDetailBy(int exchangeCode) {
        FranExchangeDetailVO franExchangeDetail = exchangeMapper.selectFranExchangeDetailBy(exchangeCode);
        return franExchangeDetail;
    }

    @Override
    public List<FranExchangeStatusVO> findFranExchangeStatusBy(int exchangeCode) {
        List<FranExchangeStatusVO> franExchangeStatus = exchangeMapper.selectFranExchangeStatusBy(exchangeCode);
        return franExchangeStatus;
    }
}
