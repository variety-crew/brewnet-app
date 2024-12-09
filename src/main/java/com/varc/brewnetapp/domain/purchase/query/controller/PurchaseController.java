package com.varc.brewnetapp.domain.purchase.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.purchase.common.PageResponse;
import com.varc.brewnetapp.domain.purchase.query.dto.*;
import com.varc.brewnetapp.domain.purchase.query.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("PurchaseControllerQuery")
@RequestMapping("api/v1/hq/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("")
    @Operation(summary = "발주 내역(구매품의서) 목록 조회 API (구매품의서 코드, 기안자명, 거래처명, 창고명, 기간으로 검색 가능)" +
            " - pageNumber의 default값은 1, pageSize의 default값은 10")
    public ResponseEntity<ResponseMessage<PageResponse<List<LetterOfPurchaseDTO>>>> selectLettersOfPurchase(
                                            @RequestAttribute("loginId") String loginId,
                                            @RequestParam(required = false) Integer purchaseCode,
                                            @RequestParam(required = false) String memberName,
                                            @RequestParam(required = false) String correspondentName,
                                            @RequestParam(required = false) String storageName,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<LetterOfPurchaseDTO>> pageResponse = purchaseService
                .selectLettersOfPurchase(loginId, purchaseCode, memberName, correspondentName, storageName,
                                            startDate, endDate, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "발주 내역 목록 조회 성공", pageResponse));
    }

    @GetMapping("/unconfirmed")
    @Operation(summary = "미결재 발주 내역(구매품의서) 목록 조회 API (구매품의서 코드, 기안자명, 거래처명, 창고명, 기간으로" +
            " 검색 가능) - pageNumber의 default값은 1, pageSize의 default값은 10")
    public ResponseEntity<ResponseMessage<PageResponse<List<LetterOfPurchaseDTO>>>> selectUnconfirmedPurchase(
                                            @RequestAttribute("loginId") String loginId,
                                            @RequestParam(required = false) Integer purchaseCode,
                                            @RequestParam(required = false) String memberName,
                                            @RequestParam(required = false) String correspondentName,
                                            @RequestParam(required = false) String storageName,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<LetterOfPurchaseDTO>> pageResponse = purchaseService
                .selectUnconfirmedPurchase(loginId, purchaseCode, memberName, correspondentName, storageName,
                                            startDate, endDate, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "미결재 발주 내역 목록 조회 성공", pageResponse));
    }

    @GetMapping("/{letterOfPurchaseCode}")
    @Operation(summary = "발주 내역(구매품의서) 상세 조회 API")
    public ResponseEntity<ResponseMessage<LetterOfPurchaseDetailDTO>> selectOneLetterOfPurchase(
                                                                    @RequestAttribute("loginId") String loginId,
                                                                    @PathVariable int letterOfPurchaseCode) {

        LetterOfPurchaseDetailDTO purchase = purchaseService.selectOneLetterOfPurchase(loginId, letterOfPurchaseCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "발주 내역 상세 조회 성공", purchase));
    }

    @GetMapping("/{letterOfPurchaseCode}/approval-line")
    @Operation(summary = "발주 내역(구매품의서)의 결재라인 조회 API")
    public ResponseEntity<ResponseMessage<PurchaseApprovalLineDTO>> selectApprovalLineOfPurchase(
                                                                @RequestAttribute("loginId") String loginId,
                                                                @PathVariable int letterOfPurchaseCode) {

        PurchaseApprovalLineDTO approvalLine = purchaseService
                                                .selectApprovalLineOfOnePurchase(loginId, letterOfPurchaseCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "발주 내역의 결재라인 조회 성공", approvalLine));
    }

    @GetMapping("/total-in-stock")
    @Operation(summary = "전체 입고 품목 목록 조회 API (발주 후 입고 처리 완료 내역과 미입고 내역 모두 조회됨 / 상품 고유코드," +
            " 상품명, 거래처명, 창고명, 기간으로 검색 가능) - pageNumber의 default값은 1, pageSize의 default값은 10, " +
            "storageConfirmed가 true면 입고 처리 완료된 발주 품목")
    public ResponseEntity<ResponseMessage<PageResponse<List<ApprovedPurchaseItemDTO>>>> selectApprovedPurchaseItems(
                                            @RequestAttribute("loginId") String loginId,
                                            @RequestParam(required = false) String itemUniqueCode,
                                            @RequestParam(required = false) String itemName,
                                            @RequestParam(required = false) String correspondentName,
                                            @RequestParam(required = false) String storageName,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<ApprovedPurchaseItemDTO>> pageResponse = purchaseService
                .selectApprovedPurchaseItems(loginId, itemUniqueCode, itemName, correspondentName, storageName,
                                                startDate, endDate, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "전체 입고 품목 목록 조회 성공", pageResponse));
    }

    @GetMapping("/uncheck-in-stock")
    @Operation(summary = "입고 미확인 품목 목록 조회 API (발주 후 미입고 내역들이 조회됨 / 상품 고유코드, 상품명, 거래처명," +
            " 기간으로 검색 가능) - pageNumber의 default값은 1, pageSize의 default값은 10")
    public ResponseEntity<ResponseMessage<PageResponse<List<ApprovedPurchaseItemDTO>>>>
        selectApprovedPurchaseItemUncheck(
                                            @RequestAttribute("loginId") String loginId,
                                            @RequestParam(required = false) String itemUniqueCode,
                                            @RequestParam(required = false) String itemName,
                                            @RequestParam(required = false) String correspondentName,
                                            @RequestParam(required = false) String storageName,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<ApprovedPurchaseItemDTO>> pageResponse = purchaseService
                .selectApprovedPurchaseItemUncheck(loginId, itemUniqueCode, itemName, correspondentName, storageName,
                                                    startDate, endDate, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "입고 미확인 품목 목록 조회 성공", pageResponse));
    }
}
