package com.varc.brewnetapp.domain.item.command.application.dto;

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
public class UpdateItemRequestDTO {

    private Integer itemCode;
    private Integer subCategoryCode;
    private String name;
    private Integer purchasePrice;
    private Integer sellingPrice;
    private String imageUrl;
    private Integer safetyStock;
    private String itemUniqueCode;
    private Integer correspondentCode;
    private Boolean active;

}
