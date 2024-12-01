package com.varc.brewnetapp.domain.item.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.item.command.application.dto.CreateCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.CreateItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateSubCategoryRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value = "commandItemController")
@RequestMapping("api/v1/hq/item")
public class ItemController {


    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("")
    @Operation(summary = "상품 생성 API ")
    public ResponseEntity<ResponseMessage<Object>> createItem(@RequestPart CreateItemRequestDTO createItemRequestDTO,
        @RequestPart(required = false) MultipartFile image) {

        itemService.createItem(createItemRequestDTO, image);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "상품 생성 성공", null));
    }

    @PutMapping("")
    @Operation(summary = "상품 수정 API. 해당 API의 DTO에 active가 있으니 활성이면 true, 비활성이면 false로 보내주시면 됩니다")
    public ResponseEntity<ResponseMessage<Object>> updateItem(@RequestPart UpdateItemRequestDTO updateItemRequestDTO,
        @RequestPart(required = false) MultipartFile image) {

        itemService.updateItem(updateItemRequestDTO, image);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "상품 수정 성공", null));
    }


    @DeleteMapping("")
    @Operation(summary = "상품 삭제(비활성화랑 동일) API. 해당 API 말고 item update API로 대체 가능")
    public ResponseEntity<ResponseMessage<Object>> deleteItem(@RequestBody
    DeleteItemRequestDTO deleteItemRequestDTO) {

        itemService.deleteSubCategory(deleteItemRequestDTO);

        return ResponseEntity.ok(new ResponseMessage<>
            (200, "상품 삭제 성공", null));
    }
}
