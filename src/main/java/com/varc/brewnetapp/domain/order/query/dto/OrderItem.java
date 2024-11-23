package com.varc.brewnetapp.domain.order.query.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {

    // tbl_order_item
    private int itemCode;
    private int quantity;
    private int partSum;

    // tbl_item
    private String name;
    // 부가세 필드는 서버에서 10% 산정

    // tbl_sub_category
    private String categoryName;
}
