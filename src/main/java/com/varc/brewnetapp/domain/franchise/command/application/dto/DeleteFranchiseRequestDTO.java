package com.varc.brewnetapp.domain.franchise.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeleteFranchiseRequestDTO {
    private Integer franchiseCode;
}
