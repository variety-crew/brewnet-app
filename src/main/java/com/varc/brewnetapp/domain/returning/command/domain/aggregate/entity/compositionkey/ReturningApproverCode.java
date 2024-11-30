package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

// 반품 별 결재자들 복합키
@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ReturningApproverCode implements Serializable {
    @Column(name="return_code")
    private int returningCode;      // 반품코드

    @Column(name="member_code")
    private int memberCode;          // 회원코드
}