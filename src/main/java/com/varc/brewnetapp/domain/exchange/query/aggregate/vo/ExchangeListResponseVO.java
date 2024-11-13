package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeReason;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeListResponseVO {
    private int exchangeCode;           // 교환번호
    private String franchiseName;       // 교환요청지점
    private String itemName;            // 교환품목명
    private String reason;              // 교환사유
    private String memberCode;          // 교환담당자
    private String createdAt;           // 교환요청일자
    private String status;              // 교환상태
    private String approved;            // 교환 승인 상태

    // reason을 한글로 변환하여 반환하는 메서드
    public void setReason(ExchangeReason reason) {
        this.reason = reason.getKrName();
    }

    // status를 한글로 변환하여 반환하는 메서드
    public void setStatus(ExchangeStatus status) {
        this.status = status.getKrName();
    }

    // approved를 한글로 변환하여 반환하는 메서드
    public void setApproved(ExchangeApproval approved) {
        this.approved = approved.getKrName();
    }
}