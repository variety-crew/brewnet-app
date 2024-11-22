package com.varc.brewnetapp.domain.purchase.command.application.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseRequestDTO {

    private String comment;
    private int correspondentCode;
    private int storageCode;
    private int approverCode;
    private List<PurchaseItemDTO> items;
}

