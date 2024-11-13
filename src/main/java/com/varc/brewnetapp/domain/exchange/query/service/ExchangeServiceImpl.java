package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.dto.ExchangeDetailDto;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeItemVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListVO;
import com.varc.brewnetapp.domain.exchange.query.mapper.ExchangeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<ExchangeListVO> findExchangeList(Map<String, Object> paramMap, Pageable page) {
        // 페이징 정보 추가
        paramMap.put("offset", page.getOffset());
        paramMap.put("pageSize", page.getPageSize());

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectExchangeList(paramMap);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt(paramMap);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeList, page, count);
    }

    public ExchangeDetailVO findExchangeDetailBy(Integer exchangeCode) {


        List<ExchangeItemVO> exchangeItemVOList = exchangeMapper.selectExchangeDetailItemBy(exchangeCode);
        log.info("ExchangeServiceImple findExchangeDetailBy - exchangeItemVOList:{}", exchangeItemVOList);

        ExchangeDetailVO exchangeDetailVO = exchangeMapper.selectExchangeDetailBy(exchangeCode);
        return exchangeDetailVO;

        // DTO vs. VO
        // 언제써야하는지

//        return new ExchangeDetailVO(exchangeDetailDto.getCreatedAt(), exchangeDetailDto.getFranchiseName(),
//                exchangeDetailDto.getReason(), exchangeDetailDto.getMemberName(), exchangeDetailDto.getComment(),
//                exchangeDetailDto.getExchangeItemList(), exchangeDetailDto.getImageList(), exchangeDetailDto.getExplanation());
    }
}
