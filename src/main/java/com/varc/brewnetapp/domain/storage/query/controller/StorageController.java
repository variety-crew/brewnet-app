package com.varc.brewnetapp.domain.storage.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.storage.common.PageResponse;
import com.varc.brewnetapp.domain.storage.query.dto.StockDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDetailDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageNameDTO;
import com.varc.brewnetapp.domain.storage.query.service.StorageService;
import com.varc.brewnetapp.exception.InvalidDataException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("StorageControllerQuery")
@RequestMapping("api/v1/hq/storage")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("")
    @Operation(summary = "창고 목록 조회 API (창고명으로 검색 가능) - pageNumber의 default값은 1, pageSize의 default값은 10")
    public ResponseEntity<ResponseMessage<PageResponse<List<StorageDTO>>>> selectStorage(
                                            @RequestAttribute("loginId") String loginId,
                                            @RequestParam(required = false) String storageName,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<StorageDTO>> response = storageService
                                                    .selectStorage(loginId, storageName, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고 목록 조회 성공", response));
    }

    @GetMapping("/{storageCode}")
    @Operation(summary = "창고 상세 조회 API")
    public ResponseEntity<ResponseMessage<StorageDetailDTO>> selectOneStorage(
                                                                @RequestAttribute("loginId") String loginId,
                                                                @PathVariable int storageCode) {

        StorageDetailDTO storageDetail = storageService.selectOneStorage(loginId, storageCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고 상세 조회 성공", storageDetail));
    }

    @GetMapping("/storages")
    @Operation(summary = "창고별 상품 재고 조회 페이지에서 창고 셀렉트박스에 사용되는 창고명 리스트 조회 API")
    public ResponseEntity<ResponseMessage<List<StorageNameDTO>>> selectStorageList(
                                                                    @RequestAttribute("loginId") String loginId) {

        List<StorageNameDTO> storages = storageService.selectStorageList(loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고명 리스트 조회 성공", storages));
    }

    @GetMapping("/storage-stock")
    @Operation(summary = "창고별 상품 재고 리스트 조회 API (창고 코드는 필수(default 1) / 상품명으로 검색 가능)" +
            " - pageNumber의 default값은 1, pageSize의 default값은 10")
    public ResponseEntity<ResponseMessage<PageResponse<List<StockDTO>>>> selectAllStock(
                                            @RequestAttribute("loginId") String loginId,
                                            @RequestParam(value = "storageCode", defaultValue = "1") int storageCode,
                                            @RequestParam(required = false, value = "itemName") String itemName,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        if (storageCode == 0) throw new InvalidDataException("창고 코드는 필수값입니다.");

        PageResponse<List<StockDTO>> response = storageService
                                                .selectAllStock(loginId, storageCode, itemName, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "창고별 상품 재고 리스트 조회 성공", response));
    }
}
