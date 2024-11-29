package com.varc.brewnetapp.domain.franchise.query.service;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseMemberDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FranchiseService {

    FranchiseDTO findFranchise(Integer franchiseCode);

    Page<FranchiseDTO> findFranchiseList(Pageable page, String franchiseName, List<String> citys, String sort);

    Page<FranchiseMemberDTO> findFranchiseMemberList(Pageable page, String franchiseName, java.util.List<java.lang.String> citys, String sort);

    List<FranchiseDTO> findFranchiseListExcel(String franchiseName, List<String> citys, String sort);
}
