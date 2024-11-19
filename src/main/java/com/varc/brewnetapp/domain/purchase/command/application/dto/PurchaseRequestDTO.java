package com.varc.brewnetapp.domain.purchase.command.application.dto;

import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseRequestDTO {

    private int memberCode;
    private String comment;
    private int correspondentCode;
    private int storageCode;
    private KindOfApproval kind;
    private int approverCode;
    private List<PurchaseItemDTO> items;
}

