package com.varc.brewnetapp.domain.correspondent.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CorrespondentRequestDTO {

    private String name;
    private String address;
    private String detailAddress;
    private String email;
    private String contact;
    private String managerName;
}
