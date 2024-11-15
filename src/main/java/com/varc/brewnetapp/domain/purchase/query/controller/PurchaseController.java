package com.varc.brewnetapp.domain.purchase.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.purchase.common.PageResponse;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.query.service.PurchaseService;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseMessage<PageResponse<List<LetterOfPurchaseDTO>>>> selectLettersOfPurchase(
                                            @RequestParam(required = false) Integer purchaseCode,
                                            @RequestParam(required = false) String memberName,
                                            @RequestParam(required = false) String correspondentName,
                                            @RequestParam(required = false) String storageName,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<LetterOfPurchaseDTO>> pageResponse = purchaseService
                .selectLettersOfPurchase(purchaseCode, memberName, correspondentName, storageName,
                                            startDate, endDate, pageNumber, pageSize);

        return ResponseEntity.ok(new ResponseMessage<>(200, "발주 목록 조회 성공", pageResponse));
    }
}
