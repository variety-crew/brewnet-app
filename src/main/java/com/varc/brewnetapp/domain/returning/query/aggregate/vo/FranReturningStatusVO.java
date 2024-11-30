package com.varc.brewnetapp.domain.returning.query.aggregate.vo;

import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FranReturningStatusVO {
    private ReturningStatus status; // 반품상태
    private String processedAt;     // 처리일시
}