package com.varc.brewnetapp.domain.returning.query.service;

import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReturningService {
    Page<ReturningListVO> findReturningList(Pageable page);
}
