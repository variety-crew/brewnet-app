package com.varc.brewnetapp.domain.purchase.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.purchase.command.application.dto.*;
import com.varc.brewnetapp.domain.purchase.command.application.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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

    @PutMapping("/in-stock")
    @Operation(summary = "발주 상품 입고 처리 API (입고예정재고 -> 가용재고)")
    public ResponseEntity<ResponseMessage<Object>> changeInStockToAvailable(@RequestParam int itemCode,
                                                                            @RequestParam int purchaseCode) {

        purchaseService.changeInStockToAvailable(itemCode, purchaseCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "발주한 상품 입고 처리 성공", null));
    }

    @PostMapping("/print-export/{letterOfPurchaseCode}")
    @Operation(summary = "외부용 발주서 출력 및 출력 내역 저장 API (응답으로 발주서에 출력될 데이터 전달)")
    public ResponseEntity<ResponseMessage<PurchasePrintResponseDTO>> exportPurchasePrint(
                                                        @PathVariable int letterOfPurchaseCode,
                                                        @RequestBody ExportPurchasePrintRequestDTO printRequest) {

        PurchasePrintResponseDTO responsePrint = purchaseService
                                                        .exportPurchasePrint(letterOfPurchaseCode, printRequest);

        return ResponseEntity.ok(new ResponseMessage<>(200, "외부용 발주서 출력 성공", responsePrint));
    }

    @PutMapping("/print-in-house/{letterOfPurchaseCode}")
    @Operation(summary = "내부용 발주서 출력 API (응답으로 발주서에 출력될 데이터 전달)")
    public ResponseEntity<ResponseMessage<PurchasePrintResponseDTO>> takeInHousePurchasePrint(
                                                        @PathVariable int letterOfPurchaseCode,
                                                        @RequestBody InHousePurchasePrintRequestDTO printRequest) {

        PurchasePrintResponseDTO responsePrint = purchaseService
                                                    .takeInHousePurchasePrint(letterOfPurchaseCode, printRequest);

        return ResponseEntity.ok(new ResponseMessage<>(200, "내부용 발주서 출력 성공", responsePrint));
    }

    @PostMapping("/send")
    @Operation(summary = "구매품의서를 회계부서로 발송하는 API (실제 전송은 아니지만 버튼 클릭 시 호출됨)")
    public ResponseEntity<ResponseMessage<Object>> sendLetterOfPurchase(@RequestParam int letterOfPurchaseCode) {
        purchaseService.sendLetterOfPurchase(letterOfPurchaseCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "회계부서로 구매품의서 발송 성공", null));
    }
}
