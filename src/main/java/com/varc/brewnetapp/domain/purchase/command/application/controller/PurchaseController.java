package com.varc.brewnetapp.domain.purchase.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseCreateDTO;
import com.varc.brewnetapp.domain.purchase.command.application.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("PurchaseControllerCommand")
@RequestMapping("api/v1/hq/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    @Operation(summary = "발주(구매품의서) 등록 API")
    public ResponseEntity<ResponseMessage<Object>> createLetterOfPurchase(@RequestBody PurchaseCreateDTO newPurchase) {
        // 개발중
        return null;
    }
}
