package com.varc.brewnetapp.domain.returning.query.aggregate.vo;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FranReturningListVO {
    private int returningCode;          // 반품코드
    private ReturningStatus status;     // 교환상태
    private int orderCode;              // 주문코드
    private String itemName;            // 주문품목명
    private int sumPrice;               // 반품금액합계
    private String createdAt;           // 반품신청일자
    private String completedAt;         // 반품완료일자
}
