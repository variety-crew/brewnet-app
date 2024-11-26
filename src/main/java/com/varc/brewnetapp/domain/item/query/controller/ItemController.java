package com.varc.brewnetapp.domain.item.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.query.dto.ItemDTO;
import com.varc.brewnetapp.domain.item.query.service.ItemService;
import com.varc.brewnetapp.domain.member.query.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryItemController")
@RequestMapping("api/v1")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    @Operation(summary = "상품 목록 조회 API / query param으로 page와 size를 키값으로 데이터 보내주시면 됩니다 "
        + "/ page는 0부터 시작 / itemName은 상품명. 필수는 X / itemCode는 상품 코드(String 값입니다). 필수는 X "
        + "sort 값은 codeASC, codeDESC, nameASC, nameDESC "
        + "Response 중 itemCode는 DB 내부적인 식별자이고 itemUniqueCode가 화면상의 품목 코드입니다 /")
    public ResponseEntity<ResponseMessage<Page<ItemDTO>>> findItemList(@PageableDefault(page = 0, size = 10) Pageable page,
        @RequestParam(name = "itemName", required = false) String itemName,
        @RequestParam(name = "itemCode", required = false) String itemCode,
        @RequestParam(name = "sort", required = false) String sort,
        @RequestParam(name = "categoryCode", required = false) String categoryCode,
        @RequestParam(name = "correspondentCode", required = false) String correspondentCode) {

        Page<ItemDTO> result = itemService.findItemList(page, itemName, itemCode, sort, categoryCode, correspondentCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "상품 목록 조회 성공", result));
    }

    @GetMapping("/hq/item")
    @Operation(summary = "본사의 상품 목록 조회 API / query param으로 page와 size를 키값으로 데이터 보내주시면 됩니다 "
        + "/ page는 0부터 시작 / itemName은 상품명. 필수는 X / itemCode는 상품 코드(String 값입니다). 필수는 X "
        + "sort 값은 codeASC, codeDESC, nameASC, nameDESC "
        + "Response 중 itemCode는 DB 내부적인 식별자이고 itemUniqueCode가 화면상의 품목 코드입니다 /")
    public ResponseEntity<ResponseMessage<Page<ItemDTO>>> findHqItemList(@PageableDefault(page = 0, size = 10) Pageable page,
        @RequestParam(name = "itemName", required = false) String itemName,
        @RequestParam(name = "itemCode", required = false) String itemCode,
        @RequestParam(name = "sort", required = false) String sort,
        @RequestParam(name = "categoryCode", required = false) String categoryCode,
        @RequestParam(name = "correspondentCode", required = false) String correspondentCode) {

        Page<ItemDTO> result = itemService.findHqItemList(page, itemName, itemCode, sort, categoryCode, correspondentCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "상품 목록 조회 성공", result));
    }


}
