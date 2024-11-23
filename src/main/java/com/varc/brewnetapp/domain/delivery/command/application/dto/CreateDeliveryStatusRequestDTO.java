package com.varc.brewnetapp.domain.delivery.command.application.dto;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateDeliveryStatusRequestDTO {
    private int code;
    private DeliveryKind deliveryKind;
    private DeliveryStatus deliveryStatus;
}
