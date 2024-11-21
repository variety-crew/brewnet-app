package com.varc.brewnetapp.domain.purchase.query.dto;

import com.varc.brewnetapp.domain.purchase.common.IsApproved;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LetterOfPurchaseDetailDTO {

    private int letterOfPurchaseCode;           // 구매품의서 코드
    private String createdAt;                   // 생성일시
    private boolean Active;                     // 활성화 여부
    private IsApproved allApproved;             // 구매품의서 결재 상태
    private String memberComment;               // 기안자 첨언(특이사항)
    private int correspondentCode;              // 거래처 코드
    private String correspondentName;           // 거래처명
    private int memberCode;                     // 기안자 회원코드
    private String memberName;                  // 기안자명
    private IsApproved approverApproved;        // 결재자의 승인 여부
    private String approverComment;             // 결재자 첨언
    private int sumPrice;                       // 발주 상품들의 공급가 총액
    private int vatPrice;                       // 발주 상품들의 부가세 총액(공급가 총액 * 10%)
    private int totalPrice;                     // 발주 상품 총액(공급가 총액 + 부가세 총액)
    private int storageCode;                    // 창고 코드
    private String storageName;                 // 창고명
    private List<PurchaseItemDTO> items;        // 발주 상품 목록
}
