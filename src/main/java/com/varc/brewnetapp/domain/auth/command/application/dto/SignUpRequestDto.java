package com.varc.brewnetapp.domain.auth.command.application.dto;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private String contact;
    private String positionName;
    private String FranchiseName;
}
