package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderQueryService {

    /*
    * TODO
     *  1. 조회 파라미터
     *   1-1. 권한, 필터링, 정렬 설정
     *  2. 로직 - 권한에 따른 조회 결과 분리(동적 쿼리 사용)
     *  3. 데이터 조회 / 검색
    * */

    // for test
    Page<HQOrderDTO> getOrderListForTest(Pageable pageable,
                                         String filter,
                                         String sort);


    // requested by hq
    Page<HQOrderDTO> getOrderListForHQ(Pageable pageable,
                                       String filter,
                                       String sort,
                                       String startDate,
                                       String endDate
    );
    Page<HQOrderDTO> searchOrderListForHQ(Pageable pageable,
                                          String filter,
                                          String criteria
    );
    OrderRequestDTO printOrderRequest(int orderCode);
    OrderDetailForHQDTO getOrderDetailForHqBy(int orderCode);
    List<OrderApprovalHistoryDTO> getOrderApprovalHistories(Integer orderCode);


    // requested by franchise
    Page<FranchiseOrderDTO> getOrderListForFranchise(Pageable pageable,
                                                     String filter,
                                                     String sort,
                                                     String startDate,
                                                     String endDate,
                                                     int franchiseCode
    );
    OrderDetailForFranchiseDTO getOrderDetailForFranchiseBy(int orderCode, String loginId);


    // common
    List<OrderStatusHistory> getOrderHistoryByOrderCode(int orderCode);
    OrderStatusHistory getOrderStatusHistoryByOrderCode(int orderCode);
}
