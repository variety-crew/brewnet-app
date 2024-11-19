package com.varc.brewnetapp.domain.member.command.application.dto;

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
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String contact;
    private String positionName;
    private String FranchiseName;

}
