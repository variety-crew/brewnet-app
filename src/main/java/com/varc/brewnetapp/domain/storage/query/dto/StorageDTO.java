package com.varc.brewnetapp.domain.storage.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StorageDTO {

    private int storageCode;            // 창고코드
    private String storageName;         // 창고명
    private String address;             // 창고 주소
    private String contact;             // 창고 연락처
}
