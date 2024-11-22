package com.varc.brewnetapp.domain.storage.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StorageDetailDTO {

    private int storageCode;            // 창고코드
    private String storageName;         // 창고명
    private String address;             // 창고 주소
    private String contact;             // 창고 연락처
    private String createdAt;           // 등록일시
    private boolean active;             // 활성화 여부(가동 상태)
}
