package com.varc.brewnetapp.domain.item.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ItemDTO {


    private Integer itemCode;
    private String name;
    private Integer purchasePrice;
    private Integer sellingPrice;
    private String imageUrl;
    private String correspondentName;
    private Integer safetyStock;
    private Boolean active;
    private String itemUniqueCode;
    private String categoryName;

}
