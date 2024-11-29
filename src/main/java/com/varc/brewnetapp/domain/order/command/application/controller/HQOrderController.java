package com.varc.brewnetapp.domain.order.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.command.application.dto.DrafterRejectOrderRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.OrderApproveRequestDTO;
import com.varc.brewnetapp.domain.order.command.application.dto.MustBuyItemDTO;
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
    public ResponseEntity<ResponseMessage<Object>> orderRequestApproval(
            @PathVariable(name = "orderCode") Integer orderCode,
            @RequestAttribute(name = "loginId") String loginId,
            @RequestBody OrderApproveRequestDTO orderApproveRequestDTO
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();

        boolean isDrafted = orderService.requestApproveOrder(orderCode, memberCode, orderApproveRequestDTO);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "order approval requested successfully", null)
        );
    }

    @DeleteMapping("/request/cancel/{orderCode}")
    @Operation(summary = "가맹점 주문 요청건에 대한 일반 관리자의 상신에 대한 결재 취소")
    public ResponseEntity<ResponseMessage<Object>> cancelOrderRequestApproval(
            @PathVariable(name = "orderCode") Integer orderCode,
            @RequestAttribute(name = "loginId") String loginId
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();

        boolean isCanceled = orderService.cancelOrderApproval(orderCode, memberCode);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "order approval canceled successfully", null)
        );
    }

    @PostMapping("/reject/{orderCode}")
    @Operation(summary = "가맹점 주문 요청건에 대한 일반 관리자의 반려")
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

    @PostMapping("/must-by/item/{itemCode}")
    @Operation(summary = "필수 구매 품목 지정")
    public ResponseEntity<ResponseMessage<Object>> setOrderMustByItem(
            @RequestAttribute("loginId") String loginId,
            @PathVariable("itemCode") int itemCode,
            @RequestBody MustBuyItemDTO mustBuyItemDTO
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();

        // TODO: 필수 구매 품목 지정
        // itemService.setItemMustBy(memberCode, itemCode, mustBuyItemDTO);

        // public void setItemMustBy(memberCode, itemCode, mustBuyItemDTO) {
            // TODO: 유효한 아이템인지 확인
            //  tbl_mandatory_purchase에 추가 시, satisfied => false
        // }

        return ResponseEntity.ok(
                new ResponseMessage<>(200, "필수 구매 품목이 지정되었습니다.", null)
        );
    }

    @DeleteMapping("/must-by/item/{itemCode}")
    @Operation(summary = "필수 구매 품목 지정 해제")
    public ResponseEntity<ResponseMessage<Object>> deleteOrderMustByItem(
            @RequestAttribute("loginId") String loginId,
            @PathVariable("itemCode") int itemCode,
            @RequestBody MustBuyItemDTO deleteMustBuyItemDTO
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();

        // TODO: 필수 구매 품목 해제
        // itemService.deleteItemMustBy(memberCode, itemCode, mustBuyItemDTO);

        // public void deleteItemMustBy(memberCode, itemCode, mustBuyItemDTO) {
        // TODO: 유효한 아이템인지 확인
        //  mandatory_purchase_code로 tbl_franchise_mandatory_purchase에 존재하는 모든 값 inactivate
        // }


        return ResponseEntity.ok(
                new ResponseMessage<>(200, "필수 구매 품목이 지정되었습니다.", null)
        );
    }
}
