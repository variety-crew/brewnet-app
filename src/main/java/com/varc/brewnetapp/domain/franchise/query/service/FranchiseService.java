package com.varc.brewnetapp.domain.franchise.query.service;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FranchiseService {

    FranchiseDTO findFranchise(Integer franchiseCode);

    Page<FranchiseDTO> findFranchiseList(Pageable page, String franchiseName, List<String> citys);
}
