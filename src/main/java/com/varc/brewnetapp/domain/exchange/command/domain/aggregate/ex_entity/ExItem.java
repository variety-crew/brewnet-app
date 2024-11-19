package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "tbl_item")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExItem {
    @Id
    @Column(name = "item_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemCode; // 상품 코드, 기본 키

    @Column(name = "name", nullable = false)
    private String name; // 상품명

    @Column(name = "purchase_price", nullable = false)
    private Integer purchasePrice; // 구매 가격

    @Column(name = "selling_price", nullable = false)
    private Integer sellingPrice; // 판매 가격

    @Column(name = "image_url", length = 500)
    private String imageUrl; // 이미지 URL (null 허용)

    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock; // 안전 재고

    @Column(name = "created_at", nullable = false)
    private String createdAt; // 생성 일시

    @Column(name = "active", nullable = false)
    private Boolean active; // 활성 상태

    @Column(name = "item_unique_code", nullable = false, length = 255)
    private String itemUniqueCode; // 고유 상품 코드

    @Column(name = "category_code", nullable = false)
    private Integer categoryCode; // 카테고리 코드
}
