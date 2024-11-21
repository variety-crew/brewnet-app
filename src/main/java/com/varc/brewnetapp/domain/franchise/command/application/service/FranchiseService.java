package com.varc.brewnetapp.domain.franchise.command.application.service;

import com.varc.brewnetapp.domain.franchise.command.application.dto.CreateFranchiseRequestDTO;
import com.varc.brewnetapp.domain.franchise.command.application.dto.DeleteFranchiseRequestDTO;
import com.varc.brewnetapp.domain.franchise.command.application.dto.UpdateFranchiseRequestDTO;

public interface FranchiseService {

    void createFranchise(CreateFranchiseRequestDTO createFranchiseRequestDTO);

    void updateFranchise(UpdateFranchiseRequestDTO updateFranchiseRequestDTO);

    void deleteFranchise(DeleteFranchiseRequestDTO deleteFranchiseRequestDTO);
}
