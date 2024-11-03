package com.varc.brewnetapp.domain.auth.command.application.dto;

import com.varc.brewnetapp.domain.auth.command.domain.aggregate.RoleType;
import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private int memberCode;
    private String id;
    private String password;
    private String name;
    private String nickname;
    private HashSet<RoleType> roleSet;
}
