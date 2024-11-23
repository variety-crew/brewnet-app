package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderItemDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;

import java.util.List;

public interface OrderService {

    // 가맹점의 주문요청
    OrderRequestResponseDTO orderRequestByFranchise(
            OrderRequestDTO orderRequestRequestDTO,
            String loginId
    );

    // 주문별 품목 생성
    void addItemsPerOrder(int orderCode, List<OrderItemDTO> orderRequestRequestDTO);

    void cancelOrderRequest(Integer orderCode);

    void rejectOrderByDrafter(int orderCode, String loginId);
}
