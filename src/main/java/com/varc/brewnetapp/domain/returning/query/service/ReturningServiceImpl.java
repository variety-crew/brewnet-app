package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeApproverVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeHistoryDetailVO;
import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeHistoryVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.*;
import com.varc.brewnetapp.domain.returning.query.mapper.ReturningMapper;
import com.varc.brewnetapp.exception.ReturningNotFoundException;
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

        // DB에서 반품 목록 조회
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

        // DB에서 반품 목록 조회
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

        // DB에서 반품 목록 조회
        List<ReturningListVO> returningList = returningMapper.selectSearchReturningList(searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectSearchReturningListCnt(searchFilter, searchWord, startDate, endDate);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(returningList, page, count);
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

        // DB에서 반품 목록 조회
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

        // DB에서 반품 목록 조회
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

    @Override
    public List<FranReturningStatusVO> findFranReturningCodeStatusBy(String loginId, Integer returningCode) {
        // 해당 가맹점에서 반품신청한 내역이 맞는지 검증
        if (isValidReturningByFranchise(loginId, returningCode)) {
            List<FranReturningStatusVO> franReturningStatus = returningMapper.selectFranReturningStatusBy(returningCode);
            return franReturningStatus;
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 반품 요청 상태만 조회할 수 있습니다");
        }
    }

    @Override
    public Page<ReturningHistoryVO> findReturningHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<ReturningHistoryVO> returningHistoryList = returningMapper.selectReturningHistoryList(searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectReturningHistoryListCnt(searchFilter, searchWord, startDate, endDate);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(returningHistoryList, page, count);
    }

    @Override
    public ReturningHistoryDetailVO findReturningHistoryDetailBy(Integer returningStockHistoryCode) {
        ReturningHistoryDetailVO returningHistoryDetail = returningMapper.selectReturningHistoryDetailBy(returningStockHistoryCode);
        return returningHistoryDetail;
    }

    @Override
    public Page<RefundHistoryVO> findRefundHistoryList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page) {
        // 페이징 정보 추가
        long offset = page.getOffset();
        long pageSize = page.getPageSize();

        // DB에서 교환 목록 조회
        List<RefundHistoryVO> returningHistoryList = returningMapper.selectRefundHistoryList(searchFilter, searchWord, startDate, endDate, offset, pageSize);

        // 전체 데이터 개수 조회
        int count = returningMapper.selectRefundHistoryListCnt(searchFilter, searchWord, startDate, endDate);

        // PageImpl 객체로 감싸서 반환
        return new PageImpl<>(returningHistoryList, page, count);
    }

    @Override
    public RefundHistoryDetailVO findRefundHistoryDetailBy(Integer returningRefundHistoryCode) {
        RefundHistoryDetailVO refundHistoryDetail = returningMapper.selectRefundHistoryDetailBy(returningRefundHistoryCode);
        return refundHistoryDetail;
    }

    @Override
    public List<ReturningApproverVO> findReturningApprover(Integer returningCode) {
        List<ReturningApproverVO> returningApproverList = returningMapper.selectReturningApproverBy(returningCode);
        return returningApproverList;
    }




    /* 반품코드로 가장 최근 반풐상태(status) 1개를 조회하는 메서드 */
    // 반품취소 시, 해당 반품요청의 상태가 REQUESTED인지 조회하기 위해 사용 (컨트롤러 X)
    @Override
    public ReturningStatus findReturningLatestStatus(int returningCode) {
        ReturningStatus latestStatus = returningMapper.selectReturningLatestStatusBy(returningCode)
                .orElseThrow(() -> new ReturningNotFoundException("반품 상태를 찾을 수 없습니다."));
        return latestStatus;
    }

    // fix: 이동 필요
    /* 유저 아이디(loginId)와 주문코드(orderCode)로 해당 가맹점의 주문이 맞는지 검증하는 메서드 */
    // 가맹점 반품신청 시 유효한 요청인지 검증하기 위해 사용
    @Override
    public boolean isValidOrderByFranchise(String loginId, int orderCode) {
        return returningMapper.selectValidOrderByFranchise(loginId, orderCode);
    }

    /* 유저 아이디(loginId)와 반품코드(returningCode)로 해당 가맹점의 반품이 맞는지 검증하는 메서드 */
    // 가맹점 목록조회/가맹점 상세조회에서 유효한 요청인지 검증하기 위해 사용
    @Override
    public boolean isValidReturningByFranchise(String loginId, int orderCode) {
        return returningMapper.selectValidReturningByFranchise(loginId, orderCode);
    }

    /* 유저 아이디(loginId)로 반품신청 가능한 주문코드 목록을 찾는 메서드 */
    // 가맹점 반품신청 시 반품신청 가능한 주문 목록 찾기 위해 사용(주문에 반품신청 가능한 물품이 1건 이상인 경우에 포함됨)
    @Override
    public List<Integer> findFranAvailableReturningBy(String loginId) {
        return returningMapper.selectAvailableReturningBy(loginId);
    }

    /* 주문코드(orderCode)로 반품신청 가능한 그 주문의 상품 리스트 찾는 메서드 */
    // 가맹점 반품신청 시 선택한 주문코드에서 반품신청 가능한 상품목록을 찾기 위해 사용
    @Override
    public List<FranReturningItemVO> findFranAvailableReturningItemBy(String loginId, int orderCode) {
        // 해당 가맹점에서 주문한 내역이 맞는지 검증
        if (isValidOrderByFranchise(loginId, orderCode)) {
            return returningMapper.selectAvailableReturningItemBy(orderCode);
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 신청한 주문의 상품 리스트만 조회할 수 있습니다");
        }
    }
}
