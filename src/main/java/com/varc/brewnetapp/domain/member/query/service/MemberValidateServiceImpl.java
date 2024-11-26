package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.query.mapper.MemberValidateMapper;
import com.varc.brewnetapp.exception.MemberNotInFranchiseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberValidateServiceImpl implements MemberValidateService {
    private final MemberValidateMapper memberValidateMapper;

    @Autowired
    public MemberValidateServiceImpl(MemberValidateMapper memberValidateMapper) {
        this.memberValidateMapper = memberValidateMapper;
    }

    @Override
    public boolean checkIsMemberFromFranchise(int memberCode, int franchiseCode) {
        if (memberValidateMapper.existMemberCodeInFranchise(memberCode, franchiseCode)) {
            return true;
        } else {
            throw new MemberNotInFranchiseException("memberCode " + memberCode + " is not from franchise " + franchiseCode);
        }
    }
}
