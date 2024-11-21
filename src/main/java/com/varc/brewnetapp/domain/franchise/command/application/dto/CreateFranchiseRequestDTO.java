package com.varc.brewnetapp.domain.franchise.command.application.dto;

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
public class CreateFranchiseRequestDTO {
    private String franchiseName;
    private String address;
    private String detailAddress;
    private String contact;
    private String businessNumber;
    private String name;

}
