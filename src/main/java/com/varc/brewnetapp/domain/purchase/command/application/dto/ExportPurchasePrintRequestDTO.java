package com.varc.brewnetapp.domain.purchase.command.application.dto;

import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExportPurchasePrintRequestDTO {

    private int memberCode;
    private String reason;
}

