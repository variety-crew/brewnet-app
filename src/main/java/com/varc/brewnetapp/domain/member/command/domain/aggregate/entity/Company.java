package com.varc.brewnetapp.domain.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_code", nullable = false)
    private Integer companyCode;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "business_number", nullable = false, length = 255)
    private String businessNumber;

    @Column(name = "corporate_number", length = 255)
    private String corporateNumber;

    @Column(name = "ceo_name", nullable = false, length = 255)
    private String ceoName;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "type_of_business", nullable = false, length = 255)
    private String typeOfBusiness;

    @Column(name = "contact", nullable = false, length = 255)
    private String contact;

    @Column(name = "date_of_establishment", nullable = false, length = 255)
    private String dateOfEstablishment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
