package com.varc.brewnetapp.domain.purchase.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.query.service.PurchaseService;
import com.varc.brewnetapp.domain.purchase.query.dto.LetterOfPurchaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseMessage<List<LetterOfPurchaseDTO>>> selectLettersOfPurchase(
                                                @ModelAttribute SearchPurchaseCriteria criteria) {
        List<LetterOfPurchaseDTO> purchaseList = purchaseService.selectLettersOfPurchase(criteria);
        return ResponseEntity.ok(new ResponseMessage<>(200, "발주 목록 조회 성공", purchaseList));
    }
}
