package com.varc.brewnetapp.domain.correspondent.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchCorrespondentCriteria {

    private Integer correspondentCode;      // 거래처 코드
    private String correspondentName;       // 거래처명

    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 페이지 크기
    private int offset;                     // (현재 페이지 번호 - 1) * 페이지 크기

    public SearchCorrespondentCriteria(Integer correspondentCode, String correspondentName) {
        this.correspondentCode = correspondentCode;
        this.correspondentName = correspondentName;
    }
}
