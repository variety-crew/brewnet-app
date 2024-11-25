package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo;

import com.varc.brewnetapp.common.domain.approve.Approval;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeManagerApproveReqVO {
    private Approval approval;       // 승인여부
    private String comment;          // 첨언(비고사항)
}
