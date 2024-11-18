package com.varc.brewnetapp.domain.document.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.document.command.domain.aggregate.ApprovalKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_approval")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_code")
    private Integer approvalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private ApprovalKind kind;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "position_code", nullable = false)
    private Integer positionCode;
}