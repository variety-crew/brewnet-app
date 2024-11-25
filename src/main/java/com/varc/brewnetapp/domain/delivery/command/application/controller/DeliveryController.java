package com.varc.brewnetapp.domain.delivery.command.application.controller;

import com.varc.brewnetapp.common.ResponseMessage;
import com.varc.brewnetapp.domain.delivery.command.application.dto.CreateDeliveryStatusRequestDTO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PutMapping("/status")
    @Operation(summary = "배송 상태 변경 API. 배송 기사만 가능 / "
        + "request로는 deliveryKind(ORDER, RETURN, EXCHANGE) 값과 code값, "
        + "변경하려는 상태 정보인 status값(PICKING, PICKED, SHIPPING, SHIPPED) 중 하나를 보내 주시면 됩니다")
    public ResponseEntity<ResponseMessage<Object>> createDeliveryStatus(
        @RequestBody CreateDeliveryStatusRequestDTO createDeliveryStatusRequestDTO,
        @RequestHeader("Authorization") String accessToken) {

        deliveryService.createDeliveryStatus(createDeliveryStatusRequestDTO, accessToken);
        return ResponseEntity.ok(new ResponseMessage<>(
            200, "배송 상태 변경 성공", null));
    }
}
