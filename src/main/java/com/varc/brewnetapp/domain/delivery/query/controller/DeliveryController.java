package com.varc.brewnetapp.domain.delivery.query.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.service.DeliveryService;
import com.varc.brewnetapp.domain.document.query.dto.ApproverDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "queryDeliveryController")
@RequestMapping("api/v1/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/order")
    @Operation(summary = "주문 배송 목록 조회. 배송 기사만 가능")
    public ResponseEntity<ResponseMessage<Page<DeliveryDTO>>> findDeliveryList(@RequestParam
        DeliveryKind deliveryKind) {

        return ResponseEntity.ok(new ResponseMessage<>(200, "배송 목록 조회 성공", deliveryService.findDeliveryList(deliveryKind)));
    }
}
