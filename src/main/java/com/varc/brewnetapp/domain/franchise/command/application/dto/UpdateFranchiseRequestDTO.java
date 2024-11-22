package com.varc.brewnetapp.domain.franchise.command.application.dto;

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
public class UpdateFranchiseRequestDTO {
    private Integer franchiseCode;
    private String franchiseName;
    private String address;
    private String detailAddress;
    private String contact;
    private String businessNumber;
    private String name;

}
