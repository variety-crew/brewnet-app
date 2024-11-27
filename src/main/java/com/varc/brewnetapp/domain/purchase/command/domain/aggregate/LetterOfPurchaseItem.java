package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tbl_letter_of_purchase_item")
@IdClass(LetterOfPurchaseItemId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LetterOfPurchaseItem {

    @Id
    @Column(name = "item_code")
    private Integer itemCode;

    @Id
    @Column(name = "letter_of_purchase_code")
    private Integer letterOfPurchaseCode;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "storage_confirmed", nullable = false)
    @ColumnDefault("false")
    private Boolean storageConfirmed;
}
