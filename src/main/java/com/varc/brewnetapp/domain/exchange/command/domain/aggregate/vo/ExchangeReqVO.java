package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo;

import com.varc.brewnetapp.common.domain.exchange.ExchangeReason;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeReqVO {
    private int orderCode;                              // 주문번호
    private List<ExchangeReqItemVO> exchangeItemList;   // 교환 품목 리스트
    private ExchangeReason reason;                      // 교환사유
    private String explanation;                         // 교환사유설명
    private List<String> exchangeImageList;             // 교환 이미지 리스트
    private int sumPrice;                               // 총합 (프론트 - 품목 리스트로 계산)
}
