package com.varc.brewnetapp.domain.purchase.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseApprovalRequestDTO;
import com.varc.brewnetapp.domain.purchase.command.application.dto.PurchaseRequestDTO;
import com.varc.brewnetapp.domain.purchase.command.application.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("PurchaseControllerCommand")
@RequestMapping("api/v1/hq/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/create")
    @Operation(summary = "발주(구매품의서) 등록 API")
    public ResponseEntity<ResponseMessage<Object>> createLetterOfPurchase(@RequestBody PurchaseRequestDTO newPurchase) {
        purchaseService.createLetterOfPurchase(newPurchase);

        return ResponseEntity.ok(new ResponseMessage<>(200, "구매품의서 등록 및 결재 요청 성공", null));
    }

    @PostMapping("/cancel/{letterOfPurchaseCode}")
    @Operation(summary = "발주(구매품의서) 결재 요청 취소 API")
    public ResponseEntity<ResponseMessage<Object>> cancelLetterOfPurchase(@PathVariable int letterOfPurchaseCode) {
        purchaseService.cancelLetterOfPurchase(letterOfPurchaseCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "구매품의서 결재 요청 취소 성공", null));
    }

    @PutMapping("/approve/{letterOfPurchaseCode}")
    @Operation(summary = "발주(구매품의서) 결재 승인 API")
    public ResponseEntity<ResponseMessage<Object>> approveLetterOfPurchase(
                                                    @PathVariable int letterOfPurchaseCode,
                                                    @RequestBody PurchaseApprovalRequestDTO request) {

        purchaseService.approveLetterOfPurchase(letterOfPurchaseCode, request);

        return ResponseEntity.ok(new ResponseMessage<>(200, "구매품의서 결재 승인 성공", null));
    }

    @PutMapping("/reject/{letterOfPurchaseCode}")
    @Operation(summary = "발주(구매품의서) 결재 반려 API")
    public ResponseEntity<ResponseMessage<Object>> rejectLetterOfPurchase(
                                                    @PathVariable int letterOfPurchaseCode,
                                                    @RequestBody PurchaseApprovalRequestDTO request) {

        purchaseService.rejectLetterOfPurchase(letterOfPurchaseCode, request);

        return ResponseEntity.ok(new ResponseMessage<>(200, "구매품의서 결재 반려 성공", null));
    }
}
