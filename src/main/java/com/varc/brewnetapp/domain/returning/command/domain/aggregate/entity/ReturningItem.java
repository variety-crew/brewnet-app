package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.ReturningItemCode;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
//@Setter
@Entity
@Table(name = "tbl_return_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class ReturningItem {

    @EmbeddedId
    private ReturningItemCode returningItemCode;    // (복합키) 반품코드, 상품코드

    @Column(name = "quantity", nullable = false)
    private int quantity;                       // 수량
}
