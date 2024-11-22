package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_seal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseSeal {

    @Id
    @Column(name = "seal_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sealCode;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "company_code", nullable = false)
    private Integer companyCode;
}
