package com.varc.brewnetapp.domain.delivery.query.dto;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryDTO {

    private String deliveryKind;
    private Integer code;
    private String deliveryFranchiseName;
    private String deliveryStatus;
    private String contact;
}
