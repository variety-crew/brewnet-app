package com.varc.brewnetapp.domain.order.query.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {

    // tbl_order_item
    private String itemCode;
    private String quantity;

    // tbl_item
    private String name;
    // 부가세 필드는 서버에서 10% 산정

    // tbl_sub_category
    private String categoryName;
}
