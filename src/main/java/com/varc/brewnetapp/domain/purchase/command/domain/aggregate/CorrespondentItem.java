package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_correspondent_item")
@IdClass(CorrespondentItemId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CorrespondentItem {

    @Id
    @Column(name = "correspondent_code")
    private Integer correspondentCode;

    @Id
    @Column(name = "item_code")
    private Integer itemCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
