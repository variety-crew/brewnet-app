package com.varc.brewnetapp.domain.member.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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
public class OrderPrintDTO {

    private Integer orderPrintCode;
    private String reason;
    private String printedAt;
    private Integer memberCode;
    private String memberName;
    private String memberSignature;
    private Integer letterOfPurchaseCode;
}
