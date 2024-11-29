package com.varc.brewnetapp.domain.statistics.query.dto;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.ApprovalKind;
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
public class MyWaitApprovalDTO {
    private ApprovalKind kind;
    private String title;
    private String drafterName;
    private String date;
    private String status;
    private String approverName;
    private Integer code;

}
