package com.varc.brewnetapp.domain.franchise.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseMemberDTO {
    private Integer memberCode;
    private String memberName;
    private String contact;
    private String franchiseName;
    private String franchiseCode;
    private String email;
    private String loginId;
}
