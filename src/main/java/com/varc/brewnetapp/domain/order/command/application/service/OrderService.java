package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;

public interface OrderService {

    // 가맹점의 주문요청
    OrderRequestResponseDTO orderRequestByFranchise(OrderRequestDTO orderRequestRequestDTO);

    // 주문별 품목 생성
//    void addItemsPerOrder(ItemsPerOrderDTO orderRequestRequestDTO);
}
