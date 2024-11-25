package com.varc.brewnetapp.domain.document.query.dto;

import com.varc.brewnetapp.domain.purchase.common.PositionNameEx;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApproverMemberDTO {

    private int approverCode;               // 결재자 회원코드
    private String approverName;            // 결재자명
    private PositionNameEx positionName;    // 직급명
}
