package com.varc.brewnetapp.domain.exchange.query.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeApproverVO {
    private String approverName;        // 결재자(기안자)
    private Position position;          // 직급
    private ExchangeApproval approval;  // 결재 처리 상태
    private String createdAt;           // 처리일자
}
