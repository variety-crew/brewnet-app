package com.varc.brewnetapp.domain.item.command.application.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MustBuyItemDTO {
    private int quantity;
    private String dueDate;
}
