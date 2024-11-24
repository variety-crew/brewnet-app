package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestApproveDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderRequestRejectDTO;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/super/orders")
public class HQSuperController {
    private final OrderService orderService;
    private final MemberService memberservice;

    @Autowired
    public HQSuperController(OrderService orderService,
                             MemberService memberservice) {
        this.orderService = orderService;
        this.memberservice = memberservice;
    }

    @PostMapping("/approve/{orderCode}")
    @Operation(summary = "책임 관리자가 상신된 주문 요청에 대한 승인")
    public ResponseEntity<ResponseMessage<Object>> approveOrderRequest(
            @PathVariable(name = "orderCode") Integer orderCode,
            @RequestAttribute(name = "loginId") String loginId,
            @RequestBody OrderRequestApproveDTO orderRequestApproveDTO
    ) {
        int memberCode = memberservice.getMemberByLoginId(loginId).getMemberCode();

        boolean approved = orderService.approveOrderDraft(orderCode, memberCode, orderRequestApproveDTO);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "successfully approved order request", null)
        );
    }

    @PostMapping("/reject/{orderCode}")
    @Operation(summary = "책임 관리자가 상신된 주문 요청에 대한 거절")
    public ResponseEntity<ResponseMessage<Object>> rejectOrderRequest(
            @PathVariable String orderCode,
            @RequestAttribute String loginId,
            @RequestBody OrderRequestRejectDTO orderRequestRejectDTO
    ) {
        int memberCode = memberservice.getMemberByLoginId(loginId).getMemberCode();

        boolean rejected = orderService.rejectOrderDraft(orderCode, memberCode, orderRequestRejectDTO);
        return  ResponseEntity.ok(
                new ResponseMessage<>(200, "successfully rejected order request", null)
        );
    }
}
