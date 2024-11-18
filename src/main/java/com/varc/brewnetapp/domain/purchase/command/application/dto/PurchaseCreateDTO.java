package com.varc.brewnetapp.domain.purchase.command.application.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseCreateDTO {

    private String createdAt;
    private boolean active;
    private IsApproved approved;
    private int memberCode;
    private String memberName;
    private String comment;
    private int correspondentCode;
    private String correspondentName;
    private int storageCode;
    private String storageName;
    private KindOfApproval kind;
    private int approverCode;
    private int sumPrice;
    private List<PurchaseItemDTO> items;
}
