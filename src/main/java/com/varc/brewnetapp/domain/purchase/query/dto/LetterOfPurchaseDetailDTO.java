package com.varc.brewnetapp.domain.purchase.query.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LetterOfPurchaseDetailDTO {

    private int letterOfPurchaseCode;
    private String createdAt;
    private Boolean Active;
    private IsApproved allApproved;
    private String memberComment;
    private int correspondentCode;
    private String correspondentName;
    private int memberCode;
    private String memberName;
    private IsApproved approverApproved;
    private String approverComment;
    private int sumPrice;
    private int storageCode;
    private String storageName;
    private List<PurchaseItemDTO> items;
}
