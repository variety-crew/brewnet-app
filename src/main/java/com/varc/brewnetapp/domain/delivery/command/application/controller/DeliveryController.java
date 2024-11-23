package com.varc.brewnetapp.domain.delivery.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.delivery.command.application.service.DeliveryService;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commandDeliveryController")
@RequestMapping("api/v1/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

//    @PutMapping("/status")
//    @Operation(summary = "배송 건에 대한 배송 상태 변경. 배송 기사만 가능 / "
//        + "deliveryKind(ORDER, RETURN, EXCHANGE) 값과 code값, 변경하려는 상태 정보인 status값("
//        + "/ RETURN과 EXCHANGE는 각각의 배송 목록 결과가 합쳐져 response 됩니다")
//    public ResponseEntity<ResponseMessage<Page<DeliveryDTO>>> findDeliveryList(@PageableDefault(page = 0, size = 10) Pageable page,
//        @RequestParam DeliveryKind deliveryKind) {
//
//        return ResponseEntity.ok(new ResponseMessage<>(
//            200, "배송 목록 조회 성공", deliveryService.findDeliveryList(deliveryKind, page)));
//    }
}
