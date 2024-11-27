package com.varc.brewnetapp.domain.member.command.application.dto;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
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
public class ChangeMemberRequestDTO {

    private String password;
    private String name;
    private String email;
    private String contact;
    private PositionName positionName;
    private Integer FranchiseCode;

}
