package com.varc.brewnetapp.domain.order.query.dto;


import com.varc.brewnetapp.common.domain.order.Available;
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
    private Available available;
    private int partSumPrice;


    // tbl_item
    private String name;
    private Integer purchasePrice; // 구매단가 or 공급가액
    // 부가세 필드는 서버에서 10% 산정

    // tbl_sub_category
    private String categoryName;
}
