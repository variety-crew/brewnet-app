package com.varc.brewnetapp.domain.franchise.query.mapper;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseMemberDTO;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FranchiseMapper {

    Optional<FranchiseDTO> selectFranchise(Integer franchiseCode);

    List<FranchiseDTO> selectFranchiseList(long offset, long pageSize, String franchiseName, List<String> citys);

    int selectFranchiseWhereFranchiseNameAndCitysCnt(String franchiseName, List<String> citys);

    List<FranchiseMemberDTO> selectFranchiseMemberList(long offset, long pageSize, String franchiseName, List<String> citys);

    int selectFranchiseMemberWhereFranchiseNameAndCitysCnt(String franchiseName, List<String> citys);
}
