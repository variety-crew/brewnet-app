package com.varc.brewnetapp.domain.purchase.query.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseApproverDTO {

    private int letterOfPurchaseCode;       // 구매품의서 코드
    private int approverCode;               // 결재자 회원코드
    private IsApproved approved;            // 결재자의 결재 상태
    private String approvedAt;              // 결재일시
    private String comment;                 // 결재자의 첨언
    private String approverName;            // 결재자명
    private String positionName;            // 결재자의 직급명
}
