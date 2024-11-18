package com.varc.brewnetapp.domain.purchase.query.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseApprovalLineDTO {

    private int letterOfPurchaseCode;
    private String createdAt;                       // 기안일시
    private String memberName;                      // 기안자명
    private List<PurchaseApproverDTO> approvers;
}
