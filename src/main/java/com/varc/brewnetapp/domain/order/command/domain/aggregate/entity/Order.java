package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "tbl_order")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code")
    private Integer orderCode;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "drafter_approved", nullable = false)
    private DrafterApproved drafterApproved;

    @Column(name = "sum_price", nullable = false)
    private int sumPrice;

    @Column(name = "franchise_code", nullable = false)
    private int franchiseCode;

    @Column(name = "member_code", nullable = true)
    private Integer memberCode;

    @Column(name = "delivery_code", nullable = true)
    private Integer deliveryCode;

    // 본사의 기안 요청 전 주문 취소
    public void orderRequestCancel() {
        this.active = false;
    }
}