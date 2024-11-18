package com.varc.brewnetapp.domain.purchase.common;

public enum Status {

    REQUESTED,      // 결재 요청
    CANCELED,       // 결재 요청 취소
    APPROVED,       // 결재 승인
    REJECTED        // 결재 반려(거절)
}
