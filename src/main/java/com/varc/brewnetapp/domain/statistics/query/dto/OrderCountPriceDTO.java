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
public class OrderCountPriceDTO {

    private String date;
    private int orderCount;
    private int orderPrice;

}
