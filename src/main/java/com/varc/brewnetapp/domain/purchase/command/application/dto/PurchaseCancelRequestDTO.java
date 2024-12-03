package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseCancelRequestDTO {

    private int letterOfPurchaseCode;
}

