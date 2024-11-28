package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "tbl_return_status_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class ReturnStatusHistory {
    @Id
    @Column(name = "return_status_history_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int returningStatusHistoryCode;     // 반품 상태 이력 코드

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReturningStatus status;             // 반품상태

    @Column(name = "created_at", nullable = false)
    private String createdAt;                   // 생성일시

    @Column(name = "active", nullable = false)
    private boolean active;                     // 활성화 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_code", nullable = false)
    private Returning returning;                // 반품코드
}