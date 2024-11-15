package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FranExchangeListResVO {
    private int exchangeCode;           // 교환코드
    private String status;              // 교환상태
    private int orderCode;              // 주문코드
    private String itemName;            // 주문품목명
    private int sumPrice;               // 교환금액합계
    private String createdAt;           // 교환신청일자
    private String completedAt;         // 교환완료일자
}
