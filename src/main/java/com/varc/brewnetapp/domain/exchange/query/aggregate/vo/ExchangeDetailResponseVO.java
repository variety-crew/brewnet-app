package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeDetailResponseVO {
    private String createdAt;                       // 교환요청일자
    private String franchiseName;                   // 교환요청 지점명
    private String reason;                          // 교환사유
    private String memberName;                      // 교환요청 담당자
    private String comment;                         // 비고사항
    private List<ExchangeItemVO> exchangeItemList;  // 교환 상품 리스트
    private List<String> exchangeImageList;         // 교환 이미지 리스트
    private String explanation;                     // 교환 상세사유
}
