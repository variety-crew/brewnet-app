package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

// 반품 별 상품 복합키
@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ReturningItemCode implements Serializable {
    @Column(name="return_code")
    private int returningCode;      // 반품코드

    @Column(name="item_code")
    private int itemCode;           // 상품코드
}