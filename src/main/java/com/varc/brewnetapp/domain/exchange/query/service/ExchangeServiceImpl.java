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
    public Page<ExchangeListResponseVO> findExchangeList(Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        paramMap.put("offset", page.getOffset());
        paramMap.put("pageSize", page.getPageSize());

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectExchangeList(paramMap);

        List<ExchangeListResponseVO> exchangeListResponseList = new ArrayList<>();

        for (ExchangeListVO exchange : exchangeList) {
            ExchangeListResponseVO exchangeResponse = new ExchangeListResponseVO(
                    exchange.getExchangeCode(),
                    exchange.getFranchiseName(),
                    exchange.getItemName(),
                    exchange.getReason().getKrName(), // enum에서 한글로 변환
                    exchange.getMemberCode(),
                    exchange.getCreatedAt(),
                    exchange.getStatus().getKrName(), // enum에서 한글로 변환
                    exchange.getApproved().getKrName() // enum에서 한글로 변환
            );

            exchangeListResponseList.add(exchangeResponse);
        }

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt(paramMap);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeListResponseList, page, count);
    }

    @Override
    public Page<ExchangeListResponseVO> searchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectSearchExchangeList(searchFilter, searchWord, startDate, endDate, offset, pageSize);

        List<ExchangeListResponseVO> exchangeListResponseList = new ArrayList<>();

        for (ExchangeListVO exchange : exchangeList) {
            ExchangeListResponseVO exchangeResponse = new ExchangeListResponseVO(
                    exchange.getExchangeCode(),
                    exchange.getFranchiseName(),
                    exchange.getItemName(),
                    exchange.getReason().getKrName(), // enum에서 한글로 변환
                    exchange.getMemberCode(),
                    exchange.getCreatedAt(),
                    exchange.getStatus().getKrName(), // enum에서 한글로 변환
                    exchange.getApproved().getKrName() // enum에서 한글로 변환
            );

            exchangeListResponseList.add(exchangeResponse);
        }

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt(paramMap);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeListResponseList, page, count);
    }

    @Override
    public ExchangeDetailResponseVO findExchangeDetailBy(Integer exchangeCode) {
        ExchangeDetailVO exchangeDetail = exchangeMapper.selectExchangeDetailBy(exchangeCode);

        ExchangeDetailResponseVO exchangeDetailResponse = new ExchangeDetailResponseVO(
                exchangeDetail.getCreatedAt(),
                exchangeDetail.getFranchiseName(),
                exchangeDetail.getReason(),
                exchangeDetail.getMemberName(),
                exchangeDetail.getComment(),
                exchangeDetail.getExchangeItemList() != null ? exchangeDetail.getExchangeItemList() : new ArrayList<>(),
                exchangeDetail.getExchangeImageList() != null ? exchangeDetail.getExchangeImageList() : new ArrayList<>(),
                exchangeDetail.getExplanation());

        return exchangeDetailResponse;
    }

    @Override
    public Page<ExchangeHistoryResponseVO> findExchangeHistoryList(Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        paramMap.put("offset", page.getOffset());
        paramMap.put("pageSize", page.getPageSize());

        // DB에서 교환 목록 조회
        List<ExchangeHistoryVO> exchangeHistoryList = exchangeMapper.selectExchangeHistoryList(paramMap);

        List<ExchangeHistoryResponseVO> exchangeHistoryResponseList = new ArrayList<>();

        for (ExchangeHistoryVO exchange : exchangeHistoryList) {
            ExchangeHistoryResponseVO exchangeResponse = new ExchangeHistoryResponseVO(
                    exchange.getExchangeStockHistoryCode(),
                    exchange.getStatus().getKrName(),
                    exchange.getManager(),
                    exchange.getCreatedAt(),
                    exchange.getConfirmed().getKrName(),
                    exchange.getExchangeCode(),
                    exchange.getExchangeManager()
            );

            exchangeHistoryResponseList.add(exchangeResponse);
        }

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeHistoryListCnt(paramMap);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeHistoryResponseList, page, count);
    }

    @Override
    public Page<ExchangeHistoryResponseVO> searchExchangeHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ExchangeHistoryVO> exchangeHistoryList = exchangeMapper.selectSearchExchangeHistoryList (searchFilter, searchWord, startDate, endDate, offset, pageSize);

        List<ExchangeHistoryResponseVO> exchangeHistoryResponseList = new ArrayList<>();

        for (ExchangeHistoryVO exchange : exchangeHistoryList) {
            ExchangeHistoryResponseVO exchangeResponse = new ExchangeHistoryResponseVO(
                    exchange.getExchangeStockHistoryCode(),
                    exchange.getStatus().getKrName(),
                    exchange.getManager(),
                    exchange.getCreatedAt(),
                    exchange.getConfirmed().getKrName(),
                    exchange.getExchangeCode(),
                    exchange.getExchangeManager()
            );

            exchangeHistoryResponseList.add(exchangeResponse);
        }

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeHistoryListCnt(paramMap);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeHistoryResponseList, page, count);
    }

    @Override
    public ExchangeHistoryDetailResponseVO findExchangeHistoryDetailBy(Integer exchangeStockHistoryCode) {
        ExchangeHistoryDetailVO exchangeHistoryDetail = exchangeMapper.selectExchangeHistoryDetailBy(exchangeStockHistoryCode);

        ExchangeHistoryDetailResponseVO exchangeHistoryDetailResponse = new ExchangeHistoryDetailResponseVO(
                exchangeHistoryDetail.getExchangeCreatedAt(),
                exchangeHistoryDetail.getFranchiseName(),
                exchangeHistoryDetail.getReason().getKrName(),
                exchangeHistoryDetail.getExchangeManager(),
                exchangeHistoryDetail.getComment(),
                exchangeHistoryDetail.getCreatedAt(),
                exchangeHistoryDetail.getStatus().getKrName(),
                exchangeHistoryDetail.getManager(),
                exchangeHistoryDetail.getExchangeHistoryItemList() != null ? exchangeHistoryDetail.getExchangeHistoryItemList() : new ArrayList<>());
        return exchangeHistoryDetailResponse;
    }
}
