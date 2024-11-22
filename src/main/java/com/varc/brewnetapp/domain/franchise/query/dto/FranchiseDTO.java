package com.varc.brewnetapp.domain.franchise.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseDTO {

    private Integer franchiseCode;
    private String franchiseName;
    private String address;
    private String contact;
    private String businessNumber;
    private String name;

}
