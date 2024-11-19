package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeReason;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
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
    private ExchangeApproval approved;  // 교환 승인 상태
}
