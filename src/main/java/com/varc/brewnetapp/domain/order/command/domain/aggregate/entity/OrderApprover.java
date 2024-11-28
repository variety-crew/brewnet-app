package com.varc.brewnetapp.domain.order.command.domain.aggregate.entity;

import com.varc.brewnetapp.common.domain.order.ApprovalStatus;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderApprovalCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "tbl_order_approver")
public class OrderApprover {
    @EmbeddedId
    private OrderApprovalCode orderApprovalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false)
    private ApprovalStatus approvalStatus;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "active", nullable = false)
    private boolean active;
}
