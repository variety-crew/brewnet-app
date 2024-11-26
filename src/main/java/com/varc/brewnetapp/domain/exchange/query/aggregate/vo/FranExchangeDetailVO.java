package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.common.domain.exchange.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // 상품 조회 위해 필요
public class FranExchangeDetailVO {
    private int exchangeCode;
    private String createdAt;                           // 교환요청일자
    private ExchangeStatus status;                      // 교환상태
    private int orderCode;                              // 주문번호
    private int totalSumPrice;                          // 교환금액합계
    private String reason;                              // 교환사유
    private String explanation;                         // 교환 상세사유
    private List<String> exchangeImageList;             // 교환 이미지 리스트
    private List<FranExchangeItemVO> exchangeItemList;  // 교환 이미지 리스트
}
