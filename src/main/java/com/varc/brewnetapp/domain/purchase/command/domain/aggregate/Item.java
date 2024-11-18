package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @Column(name = "item_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "purchase_price", nullable = false)
    private Integer purchasePrice;

    @Column(name = "selling_price", nullable = false)
    private Integer sellingPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "item_unique_code", nullable = false)
    private String uniqueCode;

    @Column(name = "category_code", nullable = false)
    private Integer categoryCode;
}
