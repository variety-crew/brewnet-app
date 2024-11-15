package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Entity(name = "tbl_order")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code")
    private Integer orderCode;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private DateTime createdAt;

    @Column(name = "active")
    private boolean active;

    @Column(name = "approved")
    private OrderApprovalStatus approved;

    @Column(name = "drafter_approved")
    private ApprovalType approvalType;

}
