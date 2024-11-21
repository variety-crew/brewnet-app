package com.varc.brewnetapp.domain.franchise.query.service;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import com.varc.brewnetapp.domain.franchise.query.mapper.FranchiseMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service(value = "queryFranchiseService")
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseMapper franchiseMapper;

    public FranchiseServiceImpl(FranchiseMapper franchiseMapper) {
        this.franchiseMapper = franchiseMapper;
    }

    @Override
    @Transactional
    public FranchiseDTO findFranchise(Integer franchiseCode) {

        FranchiseDTO franchiseDTO = franchiseMapper.selectFranchise(franchiseCode)
            .orElseThrow(() -> new EmptyDataException("가맹점 정보가 없습니다"));

        return franchiseDTO;
    }
}
