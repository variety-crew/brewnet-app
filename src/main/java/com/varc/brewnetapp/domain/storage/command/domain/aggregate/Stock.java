package com.varc.brewnetapp.domain.storage.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Entity
@Table(name = "tbl_stock")
@IdClass(StockId.class)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {

    @Id
    @Column(name = "storage_code")
    private Integer storageCode;

    @Id
    @Column(name = "item_code")
    private Integer itemCode;

    @Column(name = "available_stock")
    private Integer availableStock;

    @Column(name = "out_stock")
    private Integer outStock;

    @Column(name = "in_stock")
    private Integer inStock;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
