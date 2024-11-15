package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderItemDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.*;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderItemRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderStatusHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    // 가맹점의 주문요청
    @Transactional
    @Override
    public OrderRequestResponseDTO orderRequestByFranchise(OrderRequestDTO orderRequestDTO) {
        int requestFranchiseCode = orderRequestDTO.getFranchiseCode();
        List<OrderItemDTO> requestedOrderItemDTOList = orderRequestDTO.getOrderList();

        int orderedSum = getOrderTotalSum(requestedOrderItemDTOList);

        log.debug("orderRequestDTO: {}", orderRequestDTO);
        int orderedCode = orderRepository.save(
                Order.builder()
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .approved(OrderApprovalStatus.UNCONFIRMED)
                        .sumPrice(orderedSum)
                        .franchiseCode(requestFranchiseCode)
                        .build()
        ).getOrderCode();

        // 주문별 품목 추가
        addItemsPerOrder(orderedCode, requestedOrderItemDTOList);

        // 주문 이력 수정
        updateOrderStatusTo(orderedCode, OrderStatus.REQUESTED);
        return new OrderRequestResponseDTO(orderedCode);
    }

    @Transactional
    @Override
    public void addItemsPerOrder(int orderedCode, List<OrderItemDTO> orderRequestRequestDTO) {
        orderRequestRequestDTO.forEach(
                orderItemDTO ->
                        orderItemRepository.save(
                                OrderItem.builder()
                                        .orderItemCode(
                                                OrderItemCode.builder()
                                                        .itemCode(orderItemDTO.getItemCode())
                                                        .orderCode(orderedCode)
                                                        .build()
                                        )
                                        .quantity(orderItemDTO.getQuantity())
                                        .build()
                        )
        );
    }

    // 주문 요청 취소
    @Transactional
    @Override
    public void cancelOrderRequest(Integer orderCode) {
        Order order = orderRepository.findById(orderCode).orElseThrow(IllegalArgumentException::new);
        log.debug("exist order: {}", order);

        // TODO: validate that order processed with headquater's policy
        order.orderRequestCancel();

        log.debug("order cancelled: {}", order);

        updateOrderStatusTo(orderCode, OrderStatus.CANCELED);
        log.debug("order history updated: {}", orderStatusHistoryRepository);
    }

    // 주문 상태 변화
    @Transactional
    public void updateOrderStatusTo(int orderCode, OrderStatus newOrderStatus) {
        orderStatusHistoryRepository.save(
                OrderStatusHistory.builder()
                        .orderCode(orderCode)
                        .status(newOrderStatus)
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
