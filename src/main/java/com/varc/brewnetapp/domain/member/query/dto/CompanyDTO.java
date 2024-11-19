package com.varc.brewnetapp.domain.member.query.dto;

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
public class CompanyDTO {

    private Integer companyCode;
    private String name;
    private String businessNumber;
    private String corporateNumber;
    private String ceoName;
    private String address;
    private String typeOfBusiness;
    private String contact;
    private String dateOfEstablishment;

}
