package com.varc.brewnetapp.domain.purchase.command.application.dto;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchasePrintResponseDTO {

    private int letterOfPurchaseCode;               // 발주서 코드
    private String createdAt;                       // 발주서 생성일시
    private String memberName;                      // 발주서 작성자(기안자)명
    private PositionName positionName;              // 작성자 직위
    private String companyName;                     // 본사명
    private String businessNumber;                  // 본사 사업자등록번호
    private String corporateNumber;                 // 본사 법인등록번호
    private String ceoName;                         // 본사 대표자명
    private String companyContact;                  // 본사 연락처
    private String sealImageUrl;                    // 법인 인감 이미지
    List<PurchasePrintItemDTO> items;               // 발주 상품 목록
    private int sumPrice;                           // 총 발주금액(공급가)
    private int vatSum;                             // 총 부가세
    private String correspondentName;               // 거래처명
    private String managerName;                     // 거래처 담당자명
    private String correspondentContact;            // 거래처 연락처
    private String storageName;                     // 창고명
    private String storageAddress;                  // 창고 주소
    private String storageContact;                  // 창고 연락처
}

