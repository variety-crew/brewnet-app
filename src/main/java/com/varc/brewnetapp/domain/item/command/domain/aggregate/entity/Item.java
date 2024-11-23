package com.varc.brewnetapp.domain.item.command.domain.aggregate.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    @Column(name = "item_code")
    private Integer itemCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "purchase_price", nullable = false)
    private Integer purchasePrice;

    @Column(name = "selling_price", nullable = false)
    private Integer sellingPrice;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "item_unique_code", nullable = false, length = 255)
    private String itemUniqueCode;

    @Column(name = "category_code", nullable = false)
    private Integer categoryCode;
}
