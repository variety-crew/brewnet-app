package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.Available;
import com.varc.brewnetapp.common.domain.order.OrderHistoryStatus;
import com.varc.brewnetapp.common.domain.order.OrderApprovalStatus;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.member.query.service.MemberServiceImpl;
import com.varc.brewnetapp.domain.order.command.application.dto.DrafterRejectOrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderApproveRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderItemDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.*;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderItemRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderStatusHistoryRepository;
import com.varc.brewnetapp.domain.order.query.service.OrderQueryService;
import com.varc.brewnetapp.exception.OrderNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(value = "commandOrderService")
public class OrderServiceImpl implements OrderService {
    private final MemberService memberService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    public OrderServiceImpl(
            MemberService memberService,
            OrderRepository orderRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.memberService = memberService;
        this.orderRepository = orderRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // 가맹점의 주문요청
    @Transactional
    @Override
    public OrderRequestResponseDTO orderRequestByFranchise(OrderRequestDTO orderRequestDTO, String loginId) {
        int requestFranchiseCode = memberService.getFranchiseInfoByLoginId(loginId).getFranchiseCode();
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
        orderRepository.save(
                Order.builder()
                        .orderCode(order.getOrderCode())
                        .comment("가맹점의 주문 요청 취소")
                        .createdAt(order.getCreatedAt())
                        .active(order.isActive())
                        .approvalStatus(OrderApprovalStatus.CANCELED)
                        .drafterApproved(order.getDrafterApproved())
                        .sumPrice(order.getSumPrice())
                        .franchiseCode(order.getFranchiseCode())
                        .memberCode(order.getMemberCode())
                        .deliveryCode(order.getDeliveryCode())
                        .build()
        );

        // TODO: validate
        //  1. If member_code in tbl_order is null
        //  2. If the status column in tbl_order_status_history is 'REQUEST'

        updateOrderStatusTo(orderCode, OrderHistoryStatus.CANCELED);
        log.debug("order history updated: {}", orderStatusHistoryRepository);
    }

    @Transactional
    @Override
    public boolean requestApproveOrder(
            int orderCode,
            int memberCode,
            OrderApproveRequestDTO orderApproveRequestDTO
    ) {
        Order order = orderRepository.findById(orderCode).orElseThrow(() -> new OrderNotFound("Order not found"));

        // TODO: 일반 관리자의 상신
        //  - tbl_order 수정
        //    - approval_status UNCONFIRMED인지 확인
        //    - member_code(기안자) 할당
        //  - tbl_order_status_history
        //    - status REQUESTED -> PENDING
        //  - tbl_order_approver 추가
        //    - approved -> UNCONFIRMED
        return true;
    }

    @Transactional
    public void rejectOrderByDrafter(int orderCode,
                                     DrafterRejectOrderRequestDTO drafterRejectOrderRequestDTO,
                                     String loginId) {

        // TODO: 가맹점의 주문 요청 반려
        //  - 주문 테이블에서 데이터 수정:
        //  tbl_order.APPROVAL_STATUS           - 'UNCONFIRMED' -> 'REJECTED'
        //  tbl_order.DRAFTER_APPROVED          - 'NONE' -> 'REJECT'
        //  tbl_order.COMMENT                   - 사유 입력
        //  tbl_order.MEMBER_CODE               - 반려자(담당자) 코드 추가
        //  - 주문 상태 내역 테이블 수정:
        //  tbl_order_status_history.status     - 'REQUESTED' -> 'REJECTED'
        //  tbl_order_status_history.CREATED_AT
        //  - 주문 별 상품 테이블 수정:
        //  tbl_order_item.available            - 'AVAILABLE' -> 'UNAVAILABLE'

        String reason = drafterRejectOrderRequestDTO.getReason();
        int drafterMemberCode = memberService.getMemberByLoginId(loginId).getMemberCode();
        Order targetOrder = orderRepository.findById(orderCode).orElseThrow(() -> new OrderNotFound("Order not found"));
        List<OrderItem> orderItemList = orderItemRepository.findByOrderItemCode_OrderCode(orderCode);



        orderRepository.save(
                Order.builder()
                        .orderCode(targetOrder.getOrderCode())
                        .comment(reason)
                        .createdAt(targetOrder.getCreatedAt())
                        .active(targetOrder.isActive())
                        .approvalStatus(OrderApprovalStatus.REJECTED)
                        .drafterApproved(DrafterApproved.REJECT)
                        .sumPrice(targetOrder.getSumPrice())
                        .franchiseCode(targetOrder.getFranchiseCode())
                        .memberCode(drafterMemberCode)
                        .build()
        );

        orderStatusHistoryRepository.save(
                OrderStatusHistory.builder()
                        .status(OrderHistoryStatus.REJECTED)
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .orderCode(orderCode)
                        .build()
        );

        List<OrderItem> newOrderItemList = new ArrayList<>();
        orderItemList.forEach(
                orderItem ->
                        newOrderItemList.add(
                                OrderItem.builder()
                                        .orderItemCode(
                                                OrderItemCode.builder()
                                                        .orderCode(orderItem.getOrderItemCode().getOrderCode())
                                                        .itemCode(orderItem.getOrderItemCode().getItemCode())
                                                        .build()
                                        )
                                        .quantity(orderItem.getQuantity())
                                        .available(Available.UNAVAILABLE)
                                        .partSumPrice(orderItem.getPartSumPrice())
                                        .build()
                        )
        );
        orderItemRepository.saveAll(newOrderItemList);

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
