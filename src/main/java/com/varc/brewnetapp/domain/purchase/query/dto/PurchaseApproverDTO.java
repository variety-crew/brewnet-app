package com.varc.brewnetapp.domain.purchase.query.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseApproverDTO {

    private int letterOfPurchaseCode;
    private int approverCode;               // 결재자 회원코드
    private IsApproved approved;
    private String approvedAt;
    private String approverName;
    private String positionName;
}
