package com.varc.brewnetapp.domain.storage.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StorageRequestDTO {

    private String name;
    private String address;
    private String contact;
}
