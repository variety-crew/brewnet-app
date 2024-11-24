package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.command.application.dto.DrafterRejectOrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderApproveRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/hq/orders")
public class HQOrderController {
    private final OrderService orderService;
    private final MemberService memberService;

    @Autowired
    public HQOrderController(OrderService orderService, MemberService memberService) {
        this.orderService = orderService;
        this.memberService = memberService;
    }

    @PostMapping("/request/{orderCode}")
    @Operation(summary = "가맹점 주문 요청건에 대한 일반 관리자의 상신")
    public ResponseEntity<ResponseMessage<Object>> requestApproveOrder(
            @PathVariable Integer orderCode,
            @RequestAttribute("loginId") String loginId,
            @RequestBody OrderApproveRequestDTO orderApproveRequestDTO
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();

        boolean done = orderService.requestApproveOrder(orderCode, memberCode, orderApproveRequestDTO);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "order approval requested successfully", null)
        );
    }

    @PostMapping("/reject/{orderCode}/reject")
    public ResponseEntity<ResponseMessage<Object>> drafterReject(
            @PathVariable("orderCode") Integer orderCode,
            @RequestAttribute("loginId") String loginId,
            @RequestBody DrafterRejectOrderRequestDTO rejectOrderRequestDTO
    ) {

        orderService.rejectOrderByDrafter(orderCode, rejectOrderRequestDTO, loginId);

        return ResponseEntity.ok(
                new ResponseMessage<>(200, "order request successfully rejected", null)
        );
    }
}
