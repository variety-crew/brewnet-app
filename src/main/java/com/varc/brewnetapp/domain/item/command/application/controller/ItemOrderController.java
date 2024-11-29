package com.varc.brewnetapp.domain.item.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.command.application.service.ItemService;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hq/items/must-buy")
public class ItemOrderController {
    private final MemberService memberService;
    private final ItemService itemService;

    @Autowired
    public ItemOrderController(
            MemberService memberService,
            ItemService itemService
    ) {
        this.memberService = memberService;
        this.itemService = itemService;
    }

    @PostMapping("/set/{itemCode}")
    @Operation(summary = "필수 구매 품목 지정")
    public ResponseEntity<ResponseMessage<Void>> setOrderMustByItem(
            @PathVariable(name = "itemCode") Integer itemCode,
            @RequestAttribute(name = "loginId") String loginId,
            @RequestBody(required = true) MustBuyItemDTO mustBuyItemDTO
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();

         itemService.setMustBuyItem(
                 memberCode,
                 itemCode,
                 mustBuyItemDTO
         );

        return ResponseEntity.ok(
                new ResponseMessage<>(200, "필수 구매 품목이 지정되었습니다.", null)
        );
    }

    @PatchMapping("/update/{itemCode}")
    @Operation(summary = "필수 구매 품목 설정 수정")
    public ResponseEntity<ResponseMessage<Object>> deleteOrderMustByItem(
            @RequestAttribute("loginId") String loginId,
            @PathVariable("itemCode") int itemCode,
            @RequestBody MustBuyItemDTO mustBuyItemDTO
    ) {
        int memberCode = memberService.getMemberByLoginId(loginId).getMemberCode();
        itemService.updateMustByItem(
                memberCode,
                itemCode,
                mustBuyItemDTO
        );

        return ResponseEntity.ok(
                new ResponseMessage<>(200, "필수 구매 품목이 수정되었습니다.", null)
        );
    }
}
