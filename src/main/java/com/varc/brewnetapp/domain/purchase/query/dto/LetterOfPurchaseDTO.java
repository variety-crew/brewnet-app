package com.varc.brewnetapp.domain.purchase.query.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LetterOfPurchaseDTO {

    private int purchaseCode;
    private IsApproved approved;
    private String createdAt;
    private int sumPrice;
    private String memberName;
    private String approvedAt;
    private String storageName;
    private String correspondentName;
}
