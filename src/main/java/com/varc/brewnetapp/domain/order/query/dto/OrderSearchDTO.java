package com.varc.brewnetapp.domain.order.query.dto;

import com.varc.brewnetapp.common.SearchCriteria;
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
