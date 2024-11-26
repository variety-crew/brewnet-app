package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.domain.returning.query.aggregate.vo.FranReturningListVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningDetailVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReturningService {
    Page<ReturningListVO> findReturningList(Pageable page);

    List<ReturningListVO> findAllReturningList();

    Page<ReturningListVO> findRequestedReturningList(Pageable page);

    Page<ReturningListVO> searchReturningList(String searchFilter, String searchWord, String startDate, String endDate, Pageable page);

    ReturningDetailVO findReturningDetailBy(Integer returningCode);

    Page<FranReturningListVO> findFranReturningList(String loginId, Pageable page);

    Page<FranReturningListVO> searchFranReturningList(String loginId, String searchFilter, String searchWord, String startDate, String endDate, Pageable page);
}
