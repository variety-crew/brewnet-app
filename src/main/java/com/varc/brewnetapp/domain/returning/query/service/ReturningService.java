package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReturningService {
    Page<ReturningListVO> findReturningList(Pageable page);

    List<ReturningListVO> findAllReturningList();

    Page<ReturningListVO> findRequestedReturningList(Pageable page);
}
