package com.varc.brewnetapp.domain.storage.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchStorageCriteria {

    private Integer storageCode;            // 창고 코드
    private String storageName;             // 창고명

    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 페이지 크기
    private int offset;                     // (현재 페이지 번호 - 1) * 페이지 크기
}
