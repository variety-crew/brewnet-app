package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    /*
    * TODO
     *  1. 조회 파라미터
     *   1-1. 권한, 필터링, 정렬 설정
     *  2. 로직 - 권한에 따른 조회 결과 분리(동적 쿼리 사용)
     *  3. 리스트 조회 / 검색
    * */

    // requested by hq
    List<OrderResponseDTO> getAllOrderListByHqRequest();
    OrderResponseDTO getOrderDetailByHqWith(Integer orderCode);

    // searched by hq
    List<OrderResponseDTO> searchOrderListWithOrderCodeByHqRequest(int orderCode);
    List<OrderResponseDTO> searchOrderListWithFranchiseNameByHqRequest(String franchiseName);
    List<OrderResponseDTO> searchOrderListWithDrafterNameByHqRequest(String drafterName);


    // requested by franchise
    List<OrderResponseDTO> getAllOrderListByFranchiseRequest();
    OrderResponseDTO getOrderDetailByFranchiseWith(Integer orderCode);

    // searched by hq
    List<OrderResponseDTO> searchOrderListByFranchiseWithOrderCode(int orderCode);
}
