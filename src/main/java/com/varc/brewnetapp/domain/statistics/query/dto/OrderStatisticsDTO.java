package com.varc.brewnetapp.domain.statistics.query.dto;

import java.util.List;
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
public class OrderStatisticsDTO {

    private int orderCount;
    private int exchangeCount;
    private int returnCount;

    private List<OrderItemStatisticsDTO> items;
}
