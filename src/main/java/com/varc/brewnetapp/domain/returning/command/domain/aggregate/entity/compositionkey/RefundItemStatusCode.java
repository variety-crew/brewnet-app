package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

// 환불 상품 처리 상태 복합키
@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RefundItemStatusCode implements Serializable {

    @Column(name="return_refund_history_code")
    private int returningRefundHistoryCode;

    @Column(name="item_code")
    private int itemCode;
}