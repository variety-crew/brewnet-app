package com.varc.brewnetapp.domain.franchise.command.application.service;

import com.varc.brewnetapp.domain.franchise.command.application.dto.CreateFranchiseRequestDTO;
import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.Franchise;
import com.varc.brewnetapp.domain.franchise.command.domain.repository.FranchiseRepository;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.utility.TelNumberUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "commandFranchiseService")
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FranchiseServiceImpl(FranchiseRepository franchiseRepository, ModelMapper modelMapper) {
        this.franchiseRepository = franchiseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void createFranchise(CreateFranchiseRequestDTO createFranchiseRequestDTO) {
        String[] parts = createFranchiseRequestDTO.getAddress().split("\\s+", 2); // 첫 번째 띄어쓰기 기준으로 나누기

        Franchise franchise = modelMapper.map(createFranchiseRequestDTO, Franchise.class);
        franchise.setCreatedAt(LocalDateTime.now());
        franchise.setCity(parts[0]);
        franchise.setActive(true);
        franchise.setContact(TelNumberUtil.formatTelNumber(createFranchiseRequestDTO.getContact()));

        try {
            franchiseRepository.save(franchise);
        } catch (Exception e) {
            throw new InvalidDataException("가맹점 정보 저장에 실패했습니다");
        }

    }

    @Override
    @Transactional
    public void updateFranchise(CreateFranchiseRequestDTO createFranchiseRequestDTO) {

    }
}
