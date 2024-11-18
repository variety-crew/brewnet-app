package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.common.TelNumberUtil;
import com.varc.brewnetapp.domain.member.command.application.dto.CreateCompanyRequestDTO;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Company;
import com.varc.brewnetapp.domain.member.command.domain.repository.CompanyRepository;
import com.varc.brewnetapp.exception.InvalidApiRequestException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.util.Collection;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service(value = "commandCompanyService")
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, JwtUtil jwtUtil, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    public void createCompany(String accessToken, CreateCompanyRequestDTO createCompanyRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            if(companyRepository.findAll().size() > 1)
                throw new InvalidApiRequestException("잘못된 시도 : 이미 회사 정보가 존재하는데 추가적인 생성을 하려합니다");

            createCompanyRequestDTO.setContact(
                TelNumberUtil.formatTelNumber(createCompanyRequestDTO.getContact()));
            companyRepository.save(modelMapper.map(createCompanyRequestDTO, Company.class));
        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");

    }

    public void updateCompany(String accessToken, CreateCompanyRequestDTO createCompanyRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            List<Company> companyList = companyRepository.findAll();

            if(createCompanyRequestDTO.getContact() != null)
                companyList.get(0).setContact(TelNumberUtil.formatTelNumber(createCompanyRequestDTO.getContact()));

            if(createCompanyRequestDTO.getName() != null)
                companyList.get(0).setName(createCompanyRequestDTO.getName());

            if(createCompanyRequestDTO.getAddress() != null)
                companyList.get(0).setAddress(createCompanyRequestDTO.getAddress());

            if(createCompanyRequestDTO.getCeoName() != null)
                companyList.get(0).setCeoName(createCompanyRequestDTO.getCeoName());

            if(createCompanyRequestDTO.getCorporateNumber() != null)
                companyList.get(0).setCorporateNumber(createCompanyRequestDTO.getCorporateNumber());

            if(createCompanyRequestDTO.getBusinessNumber() != null)
                companyList.get(0).setBusinessNumber(createCompanyRequestDTO.getBusinessNumber());

            if(createCompanyRequestDTO.getDateOfEstablishment() != null)
                companyList.get(0).setDateOfEstablishment(createCompanyRequestDTO.getDateOfEstablishment());

            if(createCompanyRequestDTO.getTypeOfBusiness() != null)
                companyList.get(0).setTypeOfBusiness(createCompanyRequestDTO.getTypeOfBusiness());

            companyRepository.save(companyList.get(0));
        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");
    }
}
