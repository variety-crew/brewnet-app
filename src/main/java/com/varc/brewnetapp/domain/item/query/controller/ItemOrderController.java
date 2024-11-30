package com.varc.brewnetapp.domain.item.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.query.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.query.service.ItemService;
import com.varc.brewnetapp.domain.item.query.service.ItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/hq/items/must-buy")
public class ItemOrderController {
    private final ItemService queryItemService;

    @Autowired
    public ItemOrderController(ItemService queryItemService) {
        this.queryItemService = queryItemService;
    }

    @GetMapping
    @Operation(summary = "필수 구매 품목 리스트 조회")
    public ResponseEntity<ResponseMessage<Page<MustBuyItemDTO>>> getMustBuyItems(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        Page<MustBuyItemDTO> mustBuyItemList = queryItemService.getMustBuyItemsBy(
                pageable,
                filter,
                sort
        );

        return ResponseEntity.ok(
                new ResponseMessage<>(
                        200, "OK", mustBuyItemList
                )
        );
    }
}
