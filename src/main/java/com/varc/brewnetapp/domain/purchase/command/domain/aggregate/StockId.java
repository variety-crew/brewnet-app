package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StockId implements Serializable {              // 복합키 클래스 정의

    private Integer storageCode;
    private Integer itemCode;
}
