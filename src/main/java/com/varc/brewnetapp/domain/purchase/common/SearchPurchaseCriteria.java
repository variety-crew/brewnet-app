package com.varc.brewnetapp.domain.purchase.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchPurchaseCriteria {

    private Integer purchaseCode;           // 발주코드
    private String memberName;              // 기안자
    private String correspondentName;       // 거래처명
    private String storageName;             // 창고명
    private LocalDate startDate;            // 시작일(언제부터)
    private LocalDate endDate;              // 종료일(언제까지)
}
