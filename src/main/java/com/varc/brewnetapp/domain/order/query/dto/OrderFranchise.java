package com.varc.brewnetapp.domain.order.query.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderFranchise {
    private String franchiseCode;
    private String franchiseName;
    private String address;
    private String detailAddress;
    private String city;
    private String contact;
    private String createdAt;
}
