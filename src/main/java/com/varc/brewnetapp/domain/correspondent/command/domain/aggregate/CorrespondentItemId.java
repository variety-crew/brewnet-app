package com.varc.brewnetapp.domain.correspondent.command.domain.aggregate;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CorrespondentItemId implements Serializable {              // 복합키 클래스 정의

    private Integer correspondentCode;
    private Integer itemCode;
}
