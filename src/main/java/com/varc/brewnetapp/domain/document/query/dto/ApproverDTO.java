package com.varc.brewnetapp.domain.document.query.dto;

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
public class ApproverDTO {
    private PositionName positionName;
}
