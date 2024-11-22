package com.varc.brewnetapp.domain.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_order_print")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TempOrderPrint {

    @Id
    @Column(name = "order_print_code", nullable = false)
    private Integer orderPrintCode;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "printed_at", nullable = false, columnDefinition = "DATETIME COMMENT '생성일시와 같은 말'")
    private LocalDateTime printedAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "member_code", nullable = false)
    private Integer memberCode;

    @Column(name = "letter_of_purchase_code", nullable = false, columnDefinition = "INT COMMENT '구매품의서는 발주서의 모든 정보를 포함함'")
    private Integer letterOfPurchaseCode;
}
