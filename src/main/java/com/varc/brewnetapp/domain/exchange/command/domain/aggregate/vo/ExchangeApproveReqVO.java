package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo;

import com.varc.brewnetapp.domain.exchange.enums.ExchangeApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeDraftApproval;
import com.varc.brewnetapp.domain.exchange.enums.ExchangeReason;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeApproveReqVO {
    private int exchangeCode;               // 교환코드
    private ExchangeDraftApproval approval; // 승인여부
    private String comment;                 // 첨언(비고사항)

    private List<Integer> approverCodeList; // 결재자 목록(현재는 1명)
}
