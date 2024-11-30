package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.ReturningApproverCode;
import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Data
@Getter
@Entity
@Table(name = "tbl_return_approver")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ReturningApprover {
    @EmbeddedId
    private ReturningApproverCode returningApproverCode;  // (복합키) 회원코드, 반품코드

    @Enumerated(EnumType.STRING)
    @Column(name = "approved", nullable = false)
    private Approval approved;                      // 승인여부

    @Column(name = "created_at", nullable = true)
    private String createdAt;                       // 결재일시

    @Column(name = "comment", nullable = true)
    private String comment;                         // 비고사항

    @Column(name = "active", nullable = false)
    private boolean active;                         // 활성화
}
