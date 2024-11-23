package com.varc.brewnetapp.domain.delivery.query.dto;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryStatus;
import java.util.List;
import java.util.Map;
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
public class DeliveryDetailDTO {

    private int code;
    private DeliveryKind deliveryKind;
    private DeliveryStatus deliveryStatus;
    private List<ItemDTO> items;

}
