package com.varc.brewnetapp.domain.storage.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.storage.command.application.dto.StorageRequestDTO;
import com.varc.brewnetapp.domain.storage.command.application.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
