package com.varc.brewnetapp.domain.storage.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.storage.common.PageResponse;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDetailDTO;
import com.varc.brewnetapp.domain.storage.query.service.StorageService;
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
}
