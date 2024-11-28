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
public class updateSubCategoryRequestDTO {

    private int subCategoryCode;
    private String subCategoryName;
}
