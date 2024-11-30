package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

// 반품 완료 상품 상태(tbl_return_item_status) 복합키
@Data
@Embeddable
@EqualsAndHashCode
public class ReturningItemStatusCode implements Serializable {

    @Column(name="return_stock_history_code")
    private int returningStockHistoryCode;  // 반품완료 내역 코드

    @Column(name="item_code")
    private int itemCode;                   // 상품코드
}
