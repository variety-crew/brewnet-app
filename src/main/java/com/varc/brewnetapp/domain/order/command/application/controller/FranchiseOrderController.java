package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.orderrequest.OrderRequestResponseDTO;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/franchise/orders")
public class FranchiseOrderController {

    private final OrderService orderService;
    private final MemberService queryMemberService;

    @Autowired
    public FranchiseOrderController(
            OrderService orderService,
            MemberService queryMemberService
    ) {
        this.orderService = orderService;
        this.queryMemberService = queryMemberService;
    }

    // 주문 요청
    @PostMapping
    public ResponseEntity<ResponseMessage<OrderRequestResponseDTO>> franchiseOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @RequestAttribute("loginId") String loginId
    ) {
        OrderRequestResponseDTO orderRequestResponse = orderService.orderRequestByFranchise(orderRequestDTO, loginId);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "본사로의 주문요청이 완료됐습니다.", orderRequestResponse)
        );
    }

    // 주문 요청 취소
    @DeleteMapping("/{orderCode}")
    public ResponseEntity<ResponseMessage<Void>> cancelOrder(
            @PathVariable(name = "orderCode") Integer orderCode,
            @RequestAttribute(name = "loginId") String loginId
    ) {
        int requestMemberFranchiseCode = queryMemberService.getFranchiseInfoByLoginId(loginId)
                .getFranchiseCode();

        orderService.cancelOrderRequest(orderCode, requestMemberFranchiseCode);
        return ResponseEntity.ok(
                new ResponseMessage<>(204, "주문 요청이 취소되었습니다.", null)
        );
    }
}
