package com.varc.brewnetapp.domain.storage.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StorageNameDTO {

    private int storageCode;            // 창고코드
    private String storageName;         // 창고명
}
