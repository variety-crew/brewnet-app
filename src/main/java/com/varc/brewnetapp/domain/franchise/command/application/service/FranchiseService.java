package com.varc.brewnetapp.domain.franchise.command.application.service;

import com.varc.brewnetapp.domain.franchise.command.application.dto.CreateFranchiseRequestDTO;

public interface FranchiseService {

    void createFranchise(CreateFranchiseRequestDTO createFranchiseRequestDTO);

    void updateFranchise(CreateFranchiseRequestDTO createFranchiseRequestDTO);
}
