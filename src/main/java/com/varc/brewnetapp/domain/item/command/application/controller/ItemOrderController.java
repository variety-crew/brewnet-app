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
@RequestMapping("api/v1/hq/items/must-by")
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

    @DeleteMapping("/{itemCode}")
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
