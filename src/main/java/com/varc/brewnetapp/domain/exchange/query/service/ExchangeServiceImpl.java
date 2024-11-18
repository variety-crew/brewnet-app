package com.varc.brewnetapp.domain.exchange.query.service;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.*;
import com.varc.brewnetapp.domain.exchange.query.mapper.ExchangeMapper;
import com.varc.brewnetapp.exception.ExchangeNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
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
    public Page<ExchangeListVO> findExchangeList(Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ExchangeListVO> exchangeList = exchangeMapper.selectExchangeList(offset, pageSize);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeList, page, count);
    }

    @Override
    public Page<ExchangeListVO> searchExchangeList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
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
    public Page<ExchangeHistoryVO> findExchangeHistoryList(Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ExchangeHistoryVO> exchangeHistoryList = exchangeMapper.selectExchangeHistoryList(offset, pageSize);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectExchangeHistoryListCnt();

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(exchangeHistoryList, page, count);
    }

    @Override
    public Page<ExchangeHistoryVO> searchExchangeHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
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
    public Page<FranExchangeListVO> findFranExchangeList(String loginId, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<FranExchangeListVO> franExchangeList = exchangeMapper.selectFranExchangeList(loginId, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectFranExchangeListCnt(loginId);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(franExchangeList, page, count);
    }

    @Override
    public Page<FranExchangeListVO> searchFranExchangeList(String loginId, String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<FranExchangeListVO> franExchangeList = exchangeMapper.selectSearchFranExchangeList(loginId, searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = exchangeMapper.selectFranExchangeListCnt(loginId);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(franExchangeList, page, count);
    }

    @Override
    public FranExchangeDetailVO findFranExchangeDetailBy(String loginId, int exchangeCode) {
        // 해당 가맹점에서 교환신청한 내역이 맞는지 확인
        if (isValidExchangeByFranchise(loginId, exchangeCode)) {
            FranExchangeDetailVO franExchangeDetail = exchangeMapper.selectFranExchangeDetailBy(exchangeCode);
            return franExchangeDetail;
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 교환 요청만 조회할 수 있습니다");
        }
    }

    @Override
    public List<FranExchangeStatusVO> findFranExchangeStatusBy(String loginId, int exchangeCode) {
        if (isValidExchangeByFranchise(loginId, exchangeCode)) {
            List<FranExchangeStatusVO> franExchangeStatus = exchangeMapper.selectFranExchangeStatusBy(exchangeCode);
            return franExchangeStatus;
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 교환 요청 상태만 조회할 수 있습니다");
        }

    }

    /* 교환코드로 가장 최근 교환상태(status) 1개를 조회하는 메서드 */
    // 교환취소 시, 해당 교환요청의 상태가 REQUESTED인지 조회하기 위해 사용 (컨트롤러 X)
    @Override
    public ExchangeStatus findExchangeLatestStatus(int exchangeCode) {
        ExchangeStatus latestStatus = exchangeMapper.selectExchangeLatestStatusBy(exchangeCode)
                .orElseThrow(() -> new ExchangeNotFoundException("교환 상태를 찾을 수 없습니다."));
        return latestStatus;
    }

    /* 교환코드로 결재상황 리스트를 조회하기 위해 사용하는 메서드 */
    // (본사)교환상세보기 페이지 - '결재진행상황' 버튼 클릭 시 사용
    @Override
    public List<ExchangeApproverVO> findExchangeApprover(String loginId, int exchangeCode) {
        List<ExchangeApproverVO> exchangeApproverList = exchangeMapper.selectExchangeApproverBy(exchangeCode);
        return exchangeApproverList;
    }

    /* 유저 아이디(loginId)와 교환코드(exchangeCode)로 해당 가맹점의 주문이 맞는지 확인하는 메서드 */
    // 가맹점 목록조회/가맹점 상세조회에서 유효한 요청인지 검증하기 위해 사용
    @Override
    public boolean isValidExchangeByFranchise(String loginId, int exchangeCode) {
        return exchangeMapper.selectValidExchangeByFranchise(loginId, exchangeCode);
    }

    // fix: 이동 필요
    /* 유저 아이디(loginId)와 주문코드(orderCode)로 해당 가맹점의 주문이 맞는지 확인하는 메서드 */
    // 가맹점 교환신청 시 유효한 요청인지 검증하기 위해 사용
    @Override
    public boolean isValidOrderByFranchise(String loginId, int orderCode) {
        return exchangeMapper.selectValidExchangeByFranchise(loginId, orderCode);
    }
}
