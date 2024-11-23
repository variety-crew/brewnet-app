package com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_return_status_history")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryReturnStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_status_history_code", nullable = false)
    private Integer returnStatusHistoryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReturnStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "return_code", nullable = false)
    private Integer returnCode;

    public enum ReturnStatus {
        REQUESTED,
        CANCELED,
        APPROVED,
        REJECTED,
        PICKING,
        PICKED,
        COMPLETED
    }
}
