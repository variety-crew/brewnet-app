package com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo;

import com.varc.brewnetapp.common.domain.returning.ReturningReason;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReturningReqVO {
    private int orderCode;                              // 주문번호
    private List<ReturningReqItemVO> returningItemList; // 반품 품목 리스트
    private ReturningReason reason;                     // 반품사유
    private String explanation;                         // 반품사유설명
//    private List<String> returningImageList;            // 반품 이미지 리스트
    private int sumPrice;                               // 총합 (프론트 - 품목 리스트로 계산)
}
