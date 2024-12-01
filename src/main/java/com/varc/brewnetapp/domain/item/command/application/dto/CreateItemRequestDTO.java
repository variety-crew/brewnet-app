package com.varc.brewnetapp.domain.item.command.application.dto;

import jakarta.persistence.Column;
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
public class CreateItemRequestDTO {

    private int subCategoryCode;
    private String name;
    private int purchasePrice;
    private int sellingPrice;
    private int safetyStock;
    private String itemUniqueCode;
    private Integer correspondentCode;
}
