package com.varc.brewnetapp.domain.purchase.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_company")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyTemp {

    @Id
    @Column(name = "company_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    @Column(name = "corporate_number")
    private String corporateNumber;

    @Column(name = "ceo_name", nullable = false)
    private String ceoName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "type_of_business", nullable = false)
    private String typeOfBusiness;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "date_of_establishment")
    private String dateOfEstablishment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
