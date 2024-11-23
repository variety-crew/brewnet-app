package com.varc.brewnetapp.domain.storage.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.storage.command.application.dto.ChangeStockRequestDTO;
import com.varc.brewnetapp.domain.storage.command.application.dto.StorageRequestDTO;
import com.varc.brewnetapp.domain.storage.command.application.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("StorageControllerCommand")
@RequestMapping("api/v1/hq/storage")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/create")
    @Operation(summary = "창고 등록 API")
    public ResponseEntity<ResponseMessage<Object>> createStorage(@RequestAttribute("loginId") String loginId,
                                                                 @RequestBody StorageRequestDTO newStorage) {

        storageService.createStorage(loginId, newStorage);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고 등록 성공", null));
    }

    @PutMapping("/edit/{storageCode}")
    @Operation(summary = "창고 정보 수정 API")
    public ResponseEntity<ResponseMessage<Object>> editStorage(@RequestAttribute("loginId") String loginId,
                                                               @PathVariable int storageCode,
                                                               @RequestBody StorageRequestDTO editedStorage) {

        storageService.editStorage(loginId, storageCode, editedStorage);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고 정보 수정 성공", null));
    }

    @DeleteMapping("/delete/{storageCode}")
    @Operation(summary = "창고 삭제 API - 창고별 상품 재고가 전부 0이 아니면 삭제 불가")
    public ResponseEntity<ResponseMessage<Object>> deleteStorage(@RequestAttribute("loginId") String loginId,
                                                                 @PathVariable int storageCode) {

        storageService.deleteStorage(loginId, storageCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고 삭제 성공", null));
    }

    @PutMapping("/change-stock")
    @Operation(summary = "창고별 상품 재고 페이지에서 상품의 재고를 조정하는 API - 상품 선택 후 가용재고에 합산할 수량 입력")
    public ResponseEntity<ResponseMessage<Object>> changeStock(@RequestAttribute("loginId") String loginId,
                                                               @RequestBody List<ChangeStockRequestDTO> changes) {

        storageService.changeStock(loginId, changes);

        return ResponseEntity.ok(new ResponseMessage<>(200, "선택한 상품들의 재고 조정 성공", null));
    }
}
