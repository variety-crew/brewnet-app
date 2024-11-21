package com.varc.brewnetapp.domain.franchise.query.service;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;

public interface FranchiseService {

    FranchiseDTO findFranchise(Integer franchiseCode);
}
