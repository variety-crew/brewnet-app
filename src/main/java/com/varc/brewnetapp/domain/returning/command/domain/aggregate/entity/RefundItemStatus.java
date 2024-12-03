package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.Item;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.RefundItemStatusCode;
import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Data
@Getter
@Entity
@Table(name = "tbl_refund_item_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class RefundItemStatus {

    // 복합키
    @EmbeddedId
    private RefundItemStatusCode refundItemStatusCode;  // (복합키) 환불 완료 내역 코드, 상품코드

    @Column(name = "completed", nullable = false)
    private boolean completed;                          // 환불처리여부
}
