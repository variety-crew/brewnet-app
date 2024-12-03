package com.varc.brewnetapp.domain.storage.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StorageDeleteRequestDTO {

    private int storageCode;
}
