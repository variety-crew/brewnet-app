package com.varc.brewnetapp.domain.order.query.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDTO {
    private String itemCode;
    private String quantity;

    private String name;
    private Integer purchasePrice; // 구매단가 or 공급가액
    private Integer sellingPrice; // 판매단가 or

    // TODO: 부가세 필드는 서버에서 10% 산정

    private String categoryName;
}
