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
public class SafeStockStatisticsDTO {
    private String itemName;
    private Integer itemCode;
    private Integer availableStock;
    private Integer safeStock;
    private Integer availableMinusSafeStock;
    private Integer unApprovedOrderCount;
    private Integer minPurchaseCount;
}
