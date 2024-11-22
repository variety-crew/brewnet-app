package com.varc.brewnetapp.domain.member.command.application.service;

import com.varc.brewnetapp.common.S3ImageService;
import com.varc.brewnetapp.utility.TelNumberUtil;
import com.varc.brewnetapp.domain.member.command.application.dto.CreateCompanyRequestDTO;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Company;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Seal;
import com.varc.brewnetapp.domain.member.command.domain.repository.CompanyRepository;
import com.varc.brewnetapp.domain.member.command.domain.repository.SealRepository;
import com.varc.brewnetapp.exception.InvalidApiRequestException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "commandCompanyService")
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SealRepository sealRepository;
    private final S3ImageService s3ImageService;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyService(
        CompanyRepository companyRepository,
        SealRepository sealRepository,
        S3ImageService s3ImageService,
        JwtUtil jwtUtil,
        ModelMapper modelMapper
    ) {
        this.companyRepository = companyRepository;
        this.sealRepository = sealRepository;
        this.s3ImageService = s3ImageService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void createCompany(String accessToken, CreateCompanyRequestDTO createCompanyRequestDTO) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            if(companyRepository.findAll().size() >= 1)
                throw new InvalidApiRequestException("잘못된 시도 : 이미 회사 정보가 존재하는데 추가적인 생성을 하려합니다");

            createCompanyRequestDTO.setContact(
                TelNumberUtil.formatTelNumber(createCompanyRequestDTO.getContact()));
            Company company = modelMapper.map(createCompanyRequestDTO, Company.class);
            company.setActive(true);
            company.setCreatedAt(LocalDateTime.now());
            companyRepository.save(company);
        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");

    }

    @Transactional
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

    @Transactional
    public void createSeal(String accessToken, MultipartFile sealImage) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            List<Seal> sealList = sealRepository.findAll();

            if(sealList.size() > 0)
                throw new InvalidApiRequestException("이미 회사 법인 인감이 존재합니다. 추가로 생성할 수 없습니다");

            String s3Url = s3ImageService.upload(sealImage);

            Seal seal = Seal.builder()
                .imageUrl(s3Url)
                .createdAt(LocalDateTime.now())
                .active(true)
                .companyCode(companyRepository.findAll().get(0).getCompanyCode())
                .build();

            sealRepository.save(seal);
        } else
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");
        }

    @Transactional
    public void updateSeal(String accessToken, MultipartFile sealImage) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            List<Seal> sealList = sealRepository.findAll();

            if(sealList.size() == 0 || sealList.isEmpty())
                createSeal(accessToken, sealImage);

            //S3 이미지 삭제 기능 -> 더미 데이터의 image_url은 s3에 저장 안되어 있으므로 삭제 불가능.
//            s3ImageService.deleteImageFromS3(sealList.get(0).getImageUrl());
            String s3Url = s3ImageService.upload(sealImage);

            sealList.get(0).setImageUrl(s3Url);
            sealList.get(0).setActive(true);

            sealRepository.save(sealList.get(0));
        } else {
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");
        }
    }

    @Transactional
    public void deleteSeal(String accessToken) {
        Authentication authentication = jwtUtil.getAuthentication(accessToken.replace("Bearer ", ""));

        // 권한을 리스트 형태로 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(auth -> "ROLE_MASTER".equals(auth.getAuthority()))) {
            List<Seal> sealList = sealRepository.findAll();

            if(sealList.size() == 0 || sealList.isEmpty())
                throw new InvalidApiRequestException("삭제할 수 있는 회사 법인 인감이 없습니다");

            s3ImageService.deleteImageFromS3(sealList.get(0).getImageUrl());

            sealList.get(0).setImageUrl("");

            sealRepository.save(sealList.get(0));
            
        } else {
            throw new UnauthorizedAccessException("마스터 권한이 없는 사용자입니다");
        }
    }
}

