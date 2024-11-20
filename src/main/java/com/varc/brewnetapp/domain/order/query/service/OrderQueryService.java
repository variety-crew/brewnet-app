package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderQueryService {

    /*
    * TODO
     *  1. 조회 파라미터
     *   1-1. 권한, 필터링, 정렬 설정
     *  2. 로직 - 권한에 따른 조회 결과 분리(동적 쿼리 사용)
     *  3. 데이터 조회 / 검색
    * */

    // for test
    Page<OrderDTO> getOrderListForTest(Pageable pageable, String filter, String sort);

    // searched by hq
    Page<OrderDTO> getOrderListForHQ(Pageable pageable, String filter, String sort);

    // requested by franchise
    // TODO: get orders for franchise
    default Page<OrderDTO> getOrderListForFranchise(Pageable pageable, String filter, String sort) {
        return null;
    }

    // common

}
