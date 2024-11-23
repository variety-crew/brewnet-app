package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.Available;
import com.varc.brewnetapp.common.domain.order.OrderHistoryStatus;
import com.varc.brewnetapp.common.domain.order.OrderApprovalStatus;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderItemDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.*;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderItemRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderStatusHistoryRepository;
import com.varc.brewnetapp.exception.OrderNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service(value = "commandOrderService")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // 가맹점의 주문요청
    @Transactional
    @Override
    public OrderRequestResponseDTO orderRequestByFranchise(OrderRequestDTO orderRequestDTO) {
        int requestFranchiseCode = orderRequestDTO.getFranchiseCode();
        List<OrderItemDTO> requestedOrderItemDTOList = orderRequestDTO.getOrderList();
        log.debug("requestedOrderItemDTOList: {}", requestedOrderItemDTOList);

        int orderedSum = getOrderTotalSum(requestedOrderItemDTOList);

        int orderedCode = orderRepository.save(
                Order.builder()
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .drafterApproved(DrafterApproved.NONE)
                        .approvalStatus(OrderApprovalStatus.UNCONFIRMED)
                        .sumPrice(orderedSum)
                        .franchiseCode(requestFranchiseCode)
                        .build()
        ).getOrderCode();
        log.debug("orderedCode: {}", orderedCode);

        // 주문별 품목 추가
        addItemsPerOrder(orderedCode, requestedOrderItemDTOList);

        // 주문 내역 수정
        updateOrderStatusTo(orderedCode, OrderHistoryStatus.REQUESTED);
        return new OrderRequestResponseDTO(orderedCode);
    }

    @Transactional
    @Override
    public void addItemsPerOrder(int orderedCode, List<OrderItemDTO> orderRequestRequestDTO) {
        orderRequestRequestDTO.forEach(
                orderItemDTO -> {
                    int itemCode = orderItemDTO.getItemCode();
                    int orderQuantity = orderItemDTO.getQuantity();

                    // TODO:
                    //  int itemPrice = ItemService.findItemPriceByItemCode(itemCode);
                    //  int partPriceSum = itemPrice * orderQuantity;
                    int partPriceSum = 0;

                    orderItemRepository.save(
                            OrderItem.builder()
                                    .orderItemCode(
                                            OrderItemCode.builder()
                                                    .itemCode(itemCode)
                                                    .orderCode(orderedCode)
                                                    .build()
                                    )
                                    .quantity(orderQuantity)
                                    .available(Available.AVAILABLE)
                                    .partSumPrice(partPriceSum)
                                    .build()
                    );
                }
        );
    }

    // 주문 요청 취소
    @Transactional
    @Override
    public void cancelOrderRequest(Integer orderCode) {
        Order order = orderRepository.findById(orderCode).orElseThrow(() -> new OrderNotFound("Order not found"));

        // TODO: validate
        //  1. If member_code in tbl_order is null
        //  2. If the status column in tbl_order_status_history is 'REQUEST'

        updateOrderStatusTo(orderCode, OrderHistoryStatus.CANCELED);
        log.debug("order history updated: {}", orderStatusHistoryRepository);
    }

    // 주문 상태 변화
    @Transactional
    public void updateOrderStatusTo(int orderCode, OrderHistoryStatus newOrderHistoryStatus) {
        orderStatusHistoryRepository.save(
                OrderStatusHistory.builder()
                        .orderCode(orderCode)
                        .status(newOrderHistoryStatus)
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .build()
        );
    }

    // 주문 합계 구하기
    private int getOrderTotalSum(List<OrderItemDTO> requestedOrderItemDTOList) {
        int totalSum = 0;
        for (OrderItemDTO orderItemDTO : requestedOrderItemDTOList) {

            /* TODO: itemCode로 Item 가격 찾기
            *   int itemCode = orderItem.getItemCode();
            *   int itemPrice = itemService.getItemPriceByCode(itemCode);
            */

            int itemPrice = 100;

            int quantity = orderItemDTO.getQuantity();
            totalSum += itemPrice *quantity;
        }
        return totalSum;
    }
}
