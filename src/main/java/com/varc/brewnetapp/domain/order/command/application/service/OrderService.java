package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.domain.order.command.application.dto.DrafterRejectOrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderApproveRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestApproveDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestRejectDTO;
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
    void addItemsPerOrder(int orderCode,
                          List<OrderItemDTO> orderRequestRequestDTO);

    void cancelOrderRequest(Integer orderCode);

    void rejectOrderByDrafter(int orderCode,
                              DrafterRejectOrderRequestDTO drafterRejectOrderRequestDTO,
                              String loginId);

    // 주문 결재 상신
    boolean requestApproveOrder(int orderCode,
                                int memberCode,
                                OrderApproveRequestDTO orderApproveRequestDTO);

    // 상신된 주문에 대한 승인
    boolean approveOrderDraft(String orderCode, int memberCode, OrderRequestApproveDTO orderRequestApproveDTO);

    // 상신된 주문에 대한 반려
    boolean rejectOrderDraft(String orderCode, int memberCode, OrderRequestRejectDTO orderRequestRejectDTO);
}
