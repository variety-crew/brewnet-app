package com.varc.brewnetapp.domain.statistics.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewOrderDTO {
    private int orderCode;
    private int franchiseCode;
    private String franchiseName;
    private String itemName;
    private int totalPrice;
    private String createdAt;

}
