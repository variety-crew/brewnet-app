package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeHistoryVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListVO;
import com.varc.brewnetapp.domain.exchange.query.mapper.ExchangeMapper;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import com.varc.brewnetapp.domain.returning.query.mapper.ReturningMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ReturningServiceQuery")
@Slf4j
public class ReturningServiceImpl implements ReturningService {


    private final ReturningMapper returningMapper;

    @Autowired
    public ReturningServiceImpl(ReturningMapper returningMapper) {
        this.returningMapper = returningMapper;
    }

    @Override
    @Transactional
    public Page<ReturningListVO> findReturningList(Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ReturningListVO> returningList = returningMapper.selectReturningList(offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectReturningListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(returningList, page, count);
    }

    @Override
    public List<ReturningListVO> findAllReturningList() {
        List<ReturningListVO> returningList = returningMapper.selectAllReturningList();
        return returningList;
    }

    @Override
    public Page<ReturningListVO> findRequestedReturningList(Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ReturningListVO> returningList = returningMapper.selectRequestedReturningList(offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectRequestedReturningListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(returningList, page, count);
    }

    @Override
    public Page<ReturningListVO> searchReturningList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ReturningListVO> exchangeList = returningMapper.selectSearchReturningList(searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectSearchReturningListCnt(searchFilter, searchWord, startDate, endDate);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeList, page, count);
    }

    @Override
    public ReturningDetailVO findReturningDetailBy(Integer returningCode) {
        ReturningDetailVO returningDetail = returningMapper.selectReturningDetailBy(returningCode);
        return returningDetail;
    }
}
