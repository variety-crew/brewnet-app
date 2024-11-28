package com.varc.brewnetapp.domain.order.query.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderSearchDTO {
    private SearchCriteria criteria;
    private String searchWord;
}
