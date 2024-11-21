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

    @Column(name = "printed_at", nullable = false)
    private LocalDateTime printedAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "seal_code", nullable = false)
    private Integer sealCode;

    @Column(name = "member_code", nullable = false)
    private Integer memberCode;

    @Column(name = "letter_of_purchase_code", nullable = false)
    private Integer letterOfPurchaseCode;
}
