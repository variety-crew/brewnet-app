package com.varc.brewnetapp.domain.member.query.service;

public interface MemberValidateService {
    boolean checkIsMemberFromFranchise(int memberCode, int franchiseCode);
}
