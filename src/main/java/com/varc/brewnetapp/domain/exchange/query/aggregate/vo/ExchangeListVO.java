package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.exchange.ExchangeReason;
import com.varc.brewnetapp.common.domain.exchange.ExchangeStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeListVO {
    private int exchangeCode;           // 교환번호
    private String franchiseName;       // 교환요청지점
    private String itemName;            // 교환품목명
    private ExchangeReason reason;      // 교환사유
    private String memberCode;          // 교환담당자
    private String createdAt;           // 교환요청일자
    private ExchangeStatus status;      // 교환상태
    private Approval approved;  // 교환 승인 상태
}
