package com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReturningDrafterApproveReqVO {
    private DrafterApproved approval;       // 승인여부
    private String comment;                 // 첨언(비고사항)

    private List<Integer> approverCodeList; // 결재자 목록(현재는 1명)
}