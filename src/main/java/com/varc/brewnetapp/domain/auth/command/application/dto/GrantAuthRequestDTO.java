package com.varc.brewnetapp.domain.auth.command.application.dto;

import com.varc.brewnetapp.domain.auth.command.domain.aggregate.RoleType;
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
public class GrantAuthRequestDTO {
    private String loginId;
    private RoleType authName;

}
