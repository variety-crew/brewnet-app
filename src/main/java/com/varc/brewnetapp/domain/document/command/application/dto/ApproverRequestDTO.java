package com.varc.brewnetapp.domain.document.command.application.dto;

import com.varc.brewnetapp.domain.document.command.domain.aggregate.ApprovalKind;
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
public class ApproverRequestDTO {

    private PositionName positionName;
    private ApprovalKind kind;
    private Integer seq;
}
