package com.varc.brewnetapp.domain.correspondent.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CorrespondentDTO {

    private int correspondentCode;          // 거래처 코드
    private String correspondentName;       // 거래처명
    private String address;                 // 주소
    private String detailAddress;           // 상세주소
    private String email;                   // 이메일
    private String contact;                 // 연락처
    private String managerName;             // 거래처의 담당자명
}
