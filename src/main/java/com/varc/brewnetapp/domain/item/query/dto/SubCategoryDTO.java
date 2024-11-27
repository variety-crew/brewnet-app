package com.varc.brewnetapp.domain.item.query.dto;

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
public class SubCategoryDTO {

    private int superCategoryCode;
    private String superCategoryName;
    private int subCategoryCode;
    private String name;
}
