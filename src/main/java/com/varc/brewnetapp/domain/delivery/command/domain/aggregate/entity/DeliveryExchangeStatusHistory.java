package com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_exchange_status_history")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryExchangeStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_status_history_code", nullable = false)
    private Integer exchangeStatusHistoryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExchangeStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "exchange_code", nullable = false)
    private Integer exchangeCode;

    public enum ExchangeStatus {
        REQUESTED,
        CANCELED,
        APPROVED,
        REJECTED,
        PICKING,
        PICKED,
        SHIPPING,
        SHIPPED,
        COMPLETED
    }
}
