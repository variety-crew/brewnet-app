package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailResponseVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListResponseVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListVO;
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
public class ExchangeServiceImpl {

    private final ExchangeMapper exchangeMapper;

    @Autowired
    public ExchangeServiceImpl(ExchangeMapper exchangeMapper) {
        this.exchangeMapper = exchangeMapper;
    }

    public Page<ExchangeListResponseVO> findExchangeList(Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        paramMap.put("offset", page.getOffset());
        paramMap.put("pageSize", page.getPageSize());

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectExchangeList(paramMap);

        List<ExchangeListResponseVO> exchangeListResponseList = new ArrayList<>();

        for (ExchangeListVO exchange : exchangeList) {
            ExchangeListResponseVO exchangeRequestVO = new ExchangeListResponseVO(
                    exchange.getExchangeCode(),
                    exchange.getFranchiseName(),
                    exchange.getItemName(),
                    exchange.getReason().getKrName(), // enum에서 한글로 변환
                    exchange.getMemberCode(),
                    exchange.getCreatedAt(),
                    exchange.getStatus().getKrName(), // enum에서 한글로 변환
                    exchange.getApproved().getKrName() // enum에서 한글로 변환
            );

            exchangeListResponseList.add(exchangeRequestVO);
        }

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt(paramMap);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeListResponseList, page, count);
    }

    public ExchangeDetailResponseVO findExchangeDetailBy(Integer exchangeCode) {
        ExchangeDetailVO exchangeDetailVO = exchangeMapper.selectExchangeDetailBy(exchangeCode);

        ExchangeDetailResponseVO exchangeDetailResponseVO = new ExchangeDetailResponseVO(
                exchangeDetailVO.getCreatedAt(),
                exchangeDetailVO.getFranchiseName(),
                exchangeDetailVO.getReason(),
                exchangeDetailVO.getMemberName(),
                exchangeDetailVO.getComment(),
                exchangeDetailVO.getExchangeItemList() != null ? exchangeDetailVO.getExchangeItemList() : new ArrayList<>(),
                exchangeDetailVO.getExchangeImageList() != null ? exchangeDetailVO.getExchangeImageList() : new ArrayList<>(),
                exchangeDetailVO.getExplanation());

        return exchangeDetailResponseVO;
    }
}
