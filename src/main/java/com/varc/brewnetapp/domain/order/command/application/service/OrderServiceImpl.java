package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.Available;
import com.varc.brewnetapp.common.domain.order.OrderHistoryStatus;
import com.varc.brewnetapp.common.domain.order.OrderApprovalStatus;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.command.application.dto.DrafterRejectOrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderApproveRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestApproveDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestRejectDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderItemDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.*;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderItemRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderStatusHistoryRepository;
import com.varc.brewnetapp.domain.order.query.service.OrderValidateService;
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
    private final OrderValidateService orderValidateService;

    @Autowired
    public OrderServiceImpl(
            MemberService memberService,
            OrderRepository orderRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderItemRepository orderItemRepository,
            OrderValidateService orderValidateService
    ) {
        this.memberService = memberService;
        this.orderRepository = orderRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderValidateService = orderValidateService;
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

        if (orderValidateService.isOrderItemsMoreThenOne(orderedCode)) {
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
        } else {
            throw new OrderNotFound("Order Items should be more then one order. orderCode: " + orderedCode);
        }
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

    // 가맹점 주문 요청에 대한 일반 관리자의 상신
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
        //    - approval_status UNCONFIRMED인지 확인 (validate)
        //    - member_code(기안자) 할당된 바 있는지 확인 (validate)
        //    - member_code(기안자) 할당
        //    - drafter_approved NONE -> APPROVE
        //  - tbl_order_status_history 추가
        //    - status REQUESTED -> PENDING
        //  - tbl_order_approver 추가
        //    - approved -> UNCONFIRMED
        //  - tbl_order_item 수정
        //    - 해당 order_item의 available -> UNAVAILABLE
//        orderStatusHistoryRepository.save()
        return true;
    }

    // 일반 관리자의 주문 승인 상신 요청에 대한 책임 관리자의 승인
    @Transactional
    @Override
    public boolean approveOrderDraft(String orderCode, int memberCode, OrderRequestApproveDTO orderRequestApproveDTO) {

        // TODO: 책임 관리자의 상신에 대한 승인 처리
        //  - tbl_order_approver 수정
        //    - approved UNCONFIRMED -> APPROVED
        //    - CREATED_AT -> 수정
        //    - COMMENT -> 수정
        //  - tbl_order 수정
        //    - approval_status -> APPROVED
        //  - tbl_order_status_history 추가
        //    - STATUS -> APPROVED

        // TODO: 재고 변화
        return true;
    }

    @Transactional
    @Override
    public boolean rejectOrderDraft(String orderCode, int memberCode, OrderRequestRejectDTO orderRequestRejectDTO) {

        // TODO: 책임 관리자의 상신에 대한 반려 처리
        //  - tbl_order_approver 수정
        //    - approved UNCONFIRMED -> APPROVED
        //    - CREATED_AT -> 수정
        //    - COMMENT -> 수정
        //  - tbl_order 수정
        //    - approval_status -> APPROVED
        //  - tbl_order_status_history 추가
        //    - STATUS -> APPROVED
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
