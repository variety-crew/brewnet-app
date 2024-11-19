package com.varc.brewnetapp.domain.member.query.service;

import com.varc.brewnetapp.domain.member.query.dto.CompanyDTO;
import com.varc.brewnetapp.domain.member.query.dto.SealDTO;
import com.varc.brewnetapp.domain.member.query.mapper.MemberMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "queryCompanyService")
public class CompanyService {

    private final MemberMapper memberMapper;

    @Autowired
    public CompanyService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Transactional
    public SealDTO findCompanySeal() {
        SealDTO sealDTO = memberMapper.selectCompanySeal();
        if(sealDTO == null || sealDTO.getSealCode() == null || sealDTO.getImageUrl().equals(""))
            throw new EmptyDataException("법인 인감 정보가 없습니다");

        return sealDTO;
    }

    @Transactional
    public CompanyDTO findCompany() {
        CompanyDTO companyDTO = memberMapper.selectCompany();

        if(companyDTO == null || companyDTO.getCompanyCode() == null)
            throw new EmptyDataException("본사 정보가 없습니다");
        return companyDTO;
    }
}
