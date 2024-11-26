package com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order")
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_code", nullable = false)
    private Integer orderCode;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, columnDefinition = "ENUM('APPROVED', 'CANCELED', 'UNCONFIRMED', 'REJECTED') COMMENT '결재 확인되지 않음, 결재 취소, 결재 승인, 결재 반려'")
    private ApprovalStatus approvalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "drafter_approved", nullable = false, columnDefinition = "ENUM('APPROVE', 'REJECT', 'NONE') COMMENT '승인, 반려, 미정'")
    private DrafterApproved drafterApproved;

    @Column(name = "sum_price", nullable = false)
    private Integer sumPrice;

    @Column(name = "franchise_code", nullable = false)
    private Integer franchiseCode;

    @Column(name = "member_code")
    private Integer memberCode;

    @Column(name = "delivery_code")
    private Integer deliveryCode;

    public enum ApprovalStatus {
        APPROVED, CANCELED, UNCONFIRMED, REJECTED
    }

    public enum DrafterApproved {
        APPROVE, REJECT, NONE
    }
}
