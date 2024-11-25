package com.varc.brewnetapp.domain.returning.query.aggregate.vo;

import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.returning.ReturningReason;
import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReturningListVO {
    private int returningCode;          // 반품번호
    private String franchiseName;       // 반품요청지점
    private String itemName;            // 반품품목명
    private ReturningReason reason;     // 반품사유
    private String memberCode;          // 반품담당자
    private String createdAt;           // 반품요청일자
    private ReturningStatus status;      // 반품상태
    private Approval approvalStatus;    // 반품 승인 상태
}
