package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.FranExchangeDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningListVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import com.varc.brewnetapp.domain.returning.query.mapper.ReturningMapper;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
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

    @Override
    public Page<FranReturningListVO> findFranReturningList(String loginId, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<FranReturningListVO> franReturningList = returningMapper.selectFranReturningList(loginId, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectFranReturningListCnt(loginId);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(franReturningList, page, count);
    }

    @Override
    public Page<FranReturningListVO> searchFranReturningList(String loginId, String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<FranReturningListVO> franReturningList = returningMapper.selectSearchFranReturningList(loginId, searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectSearchFranReturningListCnt(loginId, searchFilter, searchWord, startDate, endDate);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(franReturningList, page, count);
    }

    @Override
    public FranReturningDetailVO findFranReturningDetailBy(String loginId, int returningCode) {
        // 해당 가맹점에서 반품신청한 내역이 맞는지 검증
        if (isValidReturningByFranchise(loginId, returningCode)) {
            FranReturningDetailVO franReturningDetail = returningMapper.selectFranReturningDetailBy(returningCode);
            return franReturningDetail;
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 반품 요청만 조회할 수 있습니다");
        }
    }



    /* 유저 아이디(loginId)와 반품코드(returningCode)로 해당 가맹점의 반품이 맞는지 검증하는 메서드 */
    // 가맹점 목록조회/가맹점 상세조회에서 유효한 요청인지 검증하기 위해 사용
    @Override
    public boolean isValidReturningByFranchise(String loginId, int returningCode) {
        return returningMapper.selectValidReturningByFranchise(loginId, returningCode);
    }
}
