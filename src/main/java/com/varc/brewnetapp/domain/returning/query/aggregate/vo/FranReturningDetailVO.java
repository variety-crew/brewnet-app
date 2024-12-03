package com.varc.brewnetapp.domain.returning.query.aggregate.vo;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // 상품 조회 위해 필요
public class FranReturningDetailVO {
    private int returningCode;
    private String createdAt;                   // 반품요청일자
    private ReturningStatus status;             // 반품상태
    private int orderCode;                      // 주문번호
//    private int totalSumPrice;                  // 반품금액합계
    private String reason;                      // 반품사유
    private String explanation;                 // 반품 상세사유
    private List<String> returningImageList;    // 반품 이미지 리스트
    private List<FranReturningItemVO> returningItemList;    // 반품 이미지 리스트
}
