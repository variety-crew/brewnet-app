package com.varc.brewnetapp.domain.order.command.application.service;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.ApprovalStatus;
import com.varc.brewnetapp.common.domain.order.Available;
import com.varc.brewnetapp.common.domain.order.OrderHistoryStatus;
import com.varc.brewnetapp.common.domain.order.OrderApprovalStatus;
import com.varc.brewnetapp.domain.item.query.service.ItemService;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.member.query.service.MemberServiceImpl;
import com.varc.brewnetapp.domain.order.command.application.dto.DrafterRejectOrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderApproveRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestApproveDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestRejectDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderItemDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.*;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderApprovalCode;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderApprovalRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderItemRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderRepository;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderStatusHistoryRepository;
import com.varc.brewnetapp.domain.order.query.service.OrderQueryService;
import com.varc.brewnetapp.domain.order.query.service.OrderValidateService;
import com.varc.brewnetapp.domain.storage.command.application.service.StorageService;
import com.varc.brewnetapp.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service(value = "commandOrderService")
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderApprovalRepository orderApprovalRepository;

    private final MemberService memberService;
    private final OrderQueryService orderQueryService;
    private final OrderValidateService orderValidateService;
    private final ItemService itemService;
    private final StorageService storageService;
    private final MemberServiceImpl queryMemberService;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            OrderApprovalRepository orderApprovalRepository,
            MemberService memberService,
            OrderQueryService orderQueryService,
            OrderValidateService orderValidateService,
            ItemService itemService,
            StorageService storageService, MemberServiceImpl queryMemberService
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.memberService = memberService;
        this.orderQueryService = orderQueryService;
        this.orderValidateService = orderValidateService;
        this.itemService = itemService;
        this.storageService = storageService;
        this.queryMemberService = queryMemberService;
    }

    // 가맹점의 주문요청
    @Transactional
    @Override
    public OrderRequestResponseDTO orderRequestByFranchise(
            OrderRequestDTO orderRequestDTO, String loginId
    ) {
        int requestFranchiseCode = memberService.getFranchiseInfoByLoginId(loginId).getFranchiseCode();
        List<OrderItemDTO> requestedOrderItemDTOList = orderRequestDTO.getOrderList();
        log.debug("requestedOrderItemDTOList: {}", requestedOrderItemDTOList);

        int orderedCode = orderRepository.save(
                Order.builder()
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .drafterApproved(DrafterApproved.NONE)
                        .approvalStatus(OrderApprovalStatus.UNCONFIRMED)
                        .sumPrice(0)
                        .franchiseCode(requestFranchiseCode)
                        .build()
        ).getOrderCode();

        // 주문별 품목 추가
        addItemsPerOrder(orderedCode, requestedOrderItemDTOList);

        // 주문 총 합계 update
        orderRepository.save(
                Order.builder()
                        .orderCode(orderedCode)
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .drafterApproved(DrafterApproved.NONE)
                        .approvalStatus(OrderApprovalStatus.UNCONFIRMED)
                        .sumPrice(getOrderTotalSum(requestedOrderItemDTOList))
                        .franchiseCode(requestFranchiseCode)
                        .build()
        );

        // 주문 내역 수정
        recordOrderStatusHistory(orderedCode, OrderHistoryStatus.REQUESTED);
        return new OrderRequestResponseDTO(orderedCode);
    }

    // 주문 요청 취소
    @Transactional
    @Override
    public void cancelOrderRequest(Integer orderCode, Integer requestMemberFranchiseCode) {
        Order order = orderRepository.findById(orderCode).orElseThrow(() -> new OrderNotFound("Order not found"));

        int targetFranchiseCode = order.getFranchiseCode();

        // TODO: validate
        //  If the requester is from target franchise  [DONE]
        if (!Objects.equals(targetFranchiseCode, requestMemberFranchiseCode)) {
            throw new UnauthorizedAccessException(
                    "Unauthorized access." + "OrderCode: " + orderCode + " is from franchiseCode: " + targetFranchiseCode + ". Your franchise code is: " + requestMemberFranchiseCode
            );
        }

        // TODO: validate
        //  1. check if member_code in tbl_order is null and the order is valid(.active=1) [DONE]
        //  2. If the status column in tbl_order_status_history is 'REQUESTED'             [DONE]

        // TODO: Order Item 상태 수정
        //  3. tbl_order_item 목록 수정 available, active -> UNAVAILABLE, false              [DONE]

        if (orderValidateService.isOrderDrafted(orderCode)) {
            throw new OrderApprovalAlreadyExist("Order already drafted. Order code is: " + orderCode + ". Unable to cancel the order.");
        }

        String recentOrderStatus = orderQueryService.getOrderStatusHistoryByOrderCode(orderCode).getOrderHistoryStatus();
        if (!recentOrderStatus.equals(OrderHistoryStatus.REQUESTED.getValue())) {
            throw new UnexpectedOrderStatus("expected status: " + OrderHistoryStatus.REQUESTED + " but got " + recentOrderStatus);
        }

        updateOrderedItemListStatusTo(getOrderItemsByOrderCode(orderCode), Available.UNAVAILABLE);

        orderRepository.save(
                Order.builder()
                        .orderCode(order.getOrderCode())
                        .comment("가맹점의 주문 요청 취소")
                        .createdAt(order.getCreatedAt())
                        .active(order.isActive())
                        .approvalStatus(order.getApprovalStatus())
                        .drafterApproved(order.getDrafterApproved())
                        .sumPrice(order.getSumPrice())
                        .franchiseCode(order.getFranchiseCode())
                        .memberCode(order.getMemberCode())
                        .deliveryCode(order.getDeliveryCode())
                        .build()
        );

        recordOrderStatusHistory(orderCode, OrderHistoryStatus.CANCELED);
    }

    // 가맹점 주문 요청에 대한 일반 관리자의 상신
    @Transactional
    @Override
    public boolean requestApproveOrder(
            int orderCode,  // 주문 번호
            int memberCode, // 기안자 코드
            OrderApproveRequestDTO orderApproveRequestDTO   // 주문 상신 DTO
    ) {
        int targetManagerMemberCode = orderApproveRequestDTO.getSuperManagerMemberCode();

        // TODO: 일반 관리자의 상신
        //  - 상신된 주문 결재 요청이 있는지 확인 (validate)              [DONE]
        //  - 요청 대상자(책임 관리자)가 결재 라인에 있는지 확인 (validate)
        //  - tbl_order 수정                                      [DONE]
        //    - member_code(기안자) 할당된 바 있는지 확인 (validate)    [DONE]
        //    - member_code(기안자) 할당                            [DONE]
        //    - drafter_approved NONE -> APPROVE                 [DONE]
        //  - tbl_order_status_history 추가                       [DONE]
        //    - status - PENDING                                 [DONE]
        //  - tbl_order_approver 추가                             [DONE]
        //    - approved -> UNCONFIRMED                          [DONE]
        //  - tbl_order_item 수정                                 [DONE]
        //    - 해당 order_item의 available -> UNAVAILABLE         [DONE]

        Optional<OrderApprover> optionalOrderApprover = orderApprovalRepository.findById(
                OrderApprovalCode.builder()
                        .orderCode(orderCode)
                        .memberCode(targetManagerMemberCode)
                        .build()
        );

        if (optionalOrderApprover.isPresent()) {
            OrderApprover orderApprover = optionalOrderApprover.get();
            throw new OrderApprovalAlreadyExist(
                    "order approval already exist. " +
                            "already requested by memberCode:" + orderApprover.getOrderApprovalCode().getMemberCode() +
                            ", orderCode: " + orderApprover.getOrderApprovalCode().getOrderCode() + ", superManager: " + orderApprover.getOrderApprovalCode().getMemberCode()
            );
        }

        Order order = orderRepository.findById(orderCode).orElseThrow(() -> new OrderNotFound("Order not found"));
        List<OrderItem> orderItemList = getOrderItemsByOrderCode(order.getOrderCode());

        Integer presentOrderDrafterMemberCode = order.getMemberCode();
        log.debug("order.getMemberCode() - 기존 기안자: {}", presentOrderDrafterMemberCode);

        OrderApprovalStatus presentOrderStatus = order.getApprovalStatus();
        log.debug("order.getApprovalStatus() - 기존 주문 결재 상태: {}", presentOrderStatus);

        DrafterApproved presentDraftedApprovedStatus = order.getDrafterApproved();
        log.debug("order.getDrafterApproved() - 기존 기안자의 승인 상태: {}", presentDraftedApprovedStatus);



        orderRepository.save(
                Order.builder()
                        .orderCode(order.getOrderCode())
                        .comment(orderApproveRequestDTO.getComment()) // 상신 요청 첨언
                        .createdAt(order.getCreatedAt())
                        .active(order.isActive())
                        .approvalStatus(order.getApprovalStatus())
                        .drafterApproved(DrafterApproved.APPROVE)
                        .sumPrice(order.getSumPrice())
                        .franchiseCode(order.getFranchiseCode())
                        .memberCode(memberCode)     // 기안자
                        .build()
        );

        recordOrderStatusHistory(orderCode, OrderHistoryStatus.PENDING);

        orderApprovalRepository.save(
                OrderApprover.builder()
                        .orderApprovalCode(
                                OrderApprovalCode.builder()
                                        .orderCode(order.getOrderCode())
                                        .memberCode(targetManagerMemberCode)
                                        .build()
                        )
                        .approvalStatus(ApprovalStatus.UNCONFIRMED)
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .build()
        );

        updateOrderedItemListStatusTo(orderItemList, Available.UNAVAILABLE);

        return true;
    }

    // 주문요청 상신에 책임 관리자의 승인
    @Transactional
    @Override
    public boolean approveOrderDraft(int orderCode, int memberCode, OrderRequestApproveDTO orderRequestApproveDTO) {

        // TODO: 책임 관리자의 상신에 대한 승인 처리
        //  - tbl_order_approver 수정              [DONE]
        //    - approved UNCONFIRMED -> APPROVED  [DONE]
        //    - CREATED_AT -> 수정                 [DONE]
        //    - COMMENT -> 수정                    [DONE]
        //  - tbl_order 수정                       [DONE]
        //    - approval_status -> APPROVED       [DONE]
        //  - tbl_order_status_history 추가        [DONE]
        //    - STATUS -> APPROVED                [DONE]

        OrderApprover orderApprover = orderApprovalRepository.findById(
                OrderApprovalCode.builder()
                        .memberCode(memberCode)
                        .orderCode(orderCode)
                        .build()
        ).orElseThrow(() -> new OrderApprovalNotFound("Order approval not found"));
        log.info("책임 관리자의 승인 전 tbl_order_approver.approved: {}", orderApprover.getApprovalStatus());

        if (orderApprover.getApprovalStatus().equals(ApprovalStatus.APPROVED)) {
            throw new OrderDraftAlreadyApproved("order draft already approved. orderCode: " + orderCode + ", approvedManagerMemberCode: " + memberCode);
        }

        Order order = orderRepository.findById(orderCode).orElseThrow(() -> new OrderNotFound("Order not found"));
        log.info("책임 관리자의 승인 전 tbl_order.approval_status: {}", order.getApprovalStatus());
        log.info("책임 관리자의 승인 전 tbl_order.drafter_approved: {}", order.getDrafterApproved());

        String requestedComment = orderRequestApproveDTO.getComment();

        List<OrderItem> orderItemList = getOrderItemsByOrderCode(order.getOrderCode());

        orderApprovalRepository.save(
                OrderApprover.builder()
                        .orderApprovalCode(orderApprover.getOrderApprovalCode())
                        .approvalStatus(ApprovalStatus.APPROVED)
                        .createdAt(LocalDateTime.now())
                        .comment(requestedComment)
                        .active(orderApprover.isActive())
                        .build()
        );

        orderRepository.save(
                Order.builder()
                        .orderCode(order.getOrderCode())
                        .comment(order.getComment())
                        .createdAt(order.getCreatedAt())
                        .active(order.isActive())
                        .approvalStatus(OrderApprovalStatus.APPROVED)
                        .drafterApproved(order.getDrafterApproved())
                        .sumPrice(order.getSumPrice())
                        .franchiseCode(order.getFranchiseCode())
                        .memberCode(order.getMemberCode())
                        .build()
        );

        recordOrderStatusHistory(orderCode, OrderHistoryStatus.APPROVED);

        // TODO: 재고 변화
        //  현재 가용재고에 존재하는 재고에서 주문 요청 완료건에 대한 상품의 수 만큼 재고 수 변경 [DONE]
        //  시연을 위한 storageCode=1로, 추후 변경 필요
        orderItemList.forEach(
                orderItem -> storageService.setStockReadyToDepart(
                        1,      // 추후 변경 필요
                        orderItem.getOrderItemCode().getItemCode(),
                        orderItem.getQuantity()
                )
        );


        return true;
    }

    // 주문요청 상신에 책임 관리자의 반려
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

    // 일반 관리자의 가맹점의 주문 요청 반려
    @Transactional
    public void rejectOrderByDrafter(int orderCode,
                                     DrafterRejectOrderRequestDTO drafterRejectOrderRequestDTO,
                                     String loginId) {

        // TODO: 가맹점의 주문 요청 반려
        //  - 주문 테이블에서 데이터 수정:                                            [DONE]
        //  tbl_order.APPROVAL_STATUS           - 'UNCONFIRMED' -> 'REJECTED'   [DONE]
        //  tbl_order.DRAFTER_APPROVED          - 'NONE' -> 'REJECT'            [DONE]
        //  tbl_order.COMMENT                   - 사유 입력                       [DONE]
        //  tbl_order.MEMBER_CODE               - 반려자(담당자) 코드 추가           [DONE]
        //  - 주문 상태 내역 테이블 수정:                                             [DONE]
        //  tbl_order_status_history.status     - 'REQUESTED' -> 'REJECTED'     [DONE]
        //  tbl_order_status_history.CREATED_AT                                 [DONE]
        //  - 주문 별 상품 테이블 수정:                                               [DONE]
        //  tbl_order_item.available            - 'AVAILABLE' -> 'UNAVAILABLE'  [DONE]

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
        recordOrderStatusHistory(orderCode, OrderHistoryStatus.REJECTED);
        updateOrderedItemListStatusTo(orderItemList, Available.UNAVAILABLE);
    }

    // 주문 별 아이템 조회
    @Transactional
    public List<OrderItem> getOrderItemsByOrderCode(int orderCode) {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderItemCode_OrderCode(orderCode);
        if (!orderItemList.isEmpty()) {
            return orderItemList;
        } else {
            throw new OrderItemNotFound("Order not found");
        }
    }

    // 주문 별 아이템 추가
    public void addItemsPerOrder(int orderedCode, List<OrderItemDTO> orderRequestRequestDTO) {

        if (!orderRequestRequestDTO.isEmpty()) {
            orderRequestRequestDTO.forEach(
                    orderItemDTO -> {
                        int itemCode = orderItemDTO.getItemCode();
                        int orderQuantity = orderItemDTO.getQuantity();
                        int partPriceSum = getPartSumPrice(itemCode, orderQuantity);

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
            throw new InvalidOrderItems("Order Items should be more then one order. orderCode: " + orderedCode);
        }
    }

    // 주문 별 아이템 교환/반품 요청 가능 여부 수정
    public void updateOrderedItemListStatusTo(List<OrderItem> orderedItemList, Available availableStatus) {
        List<OrderItem> targetOrderedItemList = new ArrayList<>();
        orderedItemList.forEach(
                orderItem ->
                        targetOrderedItemList.add(
                                OrderItem.builder()
                                        .orderItemCode(orderItem.getOrderItemCode())
                                        .quantity(orderItem.getQuantity())
                                        .partSumPrice(orderItem.getPartSumPrice())
                                        .available(availableStatus)
                                        .build()
                        )
        );
        orderItemRepository.saveAll(targetOrderedItemList);
    }

    // 주문 내역 기록
    public void recordOrderStatusHistory(int orderCode, OrderHistoryStatus newStatus) {
        orderStatusHistoryRepository.save(
                OrderStatusHistory.builder()
                        .status(newStatus)
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .orderCode(orderCode)
                        .build()
        );
    }

    // 주문 합계 구하기
    private int getOrderTotalSum(List<OrderItemDTO> orderItemDTOList) {
        int totalSum = 0;
        for (OrderItemDTO orderItemDTO : orderItemDTOList) {

            /* TODO: itemCode로 Item 가격 찾기                                [DONE]
            *   int itemCode = orderItem.getItemCode();                    [DONE]
            *   int itemPrice = itemService.getItemPriceByCode(itemCode);  [DONE]
            */
            int itemCode = orderItemDTO.getItemCode();
            int quantity = orderItemDTO.getQuantity();

            totalSum += getPartSumPrice(itemCode, quantity);

            log.debug("itemCode: {}", itemCode);
            log.debug("quantity: {}", quantity);
            log.debug("totalSum: {}", totalSum);
        }
        return totalSum;
    }

    // 주문 아이템 부분 합 구하기
    private int getPartSumPrice(int itemCode, int quantity) {
        int sellingPrice = itemService.findItemSellingPriceByItemCode(itemCode);
        log.debug("itemCode: {}", itemCode);
        log.debug("quantity: {}", quantity);
        return sellingPrice * quantity;
    }
}
