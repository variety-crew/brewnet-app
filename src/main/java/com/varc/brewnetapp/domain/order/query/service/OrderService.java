package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.vo.hq.response.OrderResponseVO;

import java.util.List;

public interface OrderService {

    // requested by hq
    List<OrderResponseVO> getAllOrderListByHqRequest();
    OrderResponseVO getOrderDetailByHqWith(Integer orderCode);

    // searched by hq
    List<OrderResponseVO> searchOrderListWithOrderCodeByHqRequest(int orderCode);
    List<OrderResponseVO> searchOrderListWithFranchiseNameByHqRequest(String franchiseName);
    List<OrderResponseVO> searchOrderListWithDrafterNameByHqRequest(String drafterName);


    // requested by franchise
    List<OrderResponseVO> getAllOrderListByFranchiseRequest();
    OrderResponseVO getOrderDetailByFranchiseWith(Integer orderCode);

    // searched by hq
    List<OrderResponseVO> searchOrderListByFranchiseWithOrderCode(int orderCode);
}
