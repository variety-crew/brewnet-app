package com.varc.brewnetapp.domain.document.query.dto;

import com.varc.brewnetapp.domain.document.command.domain.aggregate.ApprovalKind;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Position;
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
public class ApprovalDTO {
    private ApprovalKind kind;
    private int seq;
    private PositionName positionName;
}
