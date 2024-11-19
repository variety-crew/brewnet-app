package com.varc.brewnetapp.domain.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_seal")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seal_code", nullable = false)
    private Integer sealCode;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "company_code", nullable = false)
    private Integer companyCode;
}

