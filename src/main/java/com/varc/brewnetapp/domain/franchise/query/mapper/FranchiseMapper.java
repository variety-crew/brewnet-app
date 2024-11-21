package com.varc.brewnetapp.domain.franchise.query.mapper;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FranchiseMapper {

    Optional<FranchiseDTO> selectFranchise(Integer franchiseCode);

    List<FranchiseDTO> selectFranchiseList(long offset, long pageSize, String franchiseName, List<String> citys);

    int selectFranchiseWhereFranchiseNameAndCitysCnt(String franchiseName, List<String> citys);

    int selectFranchiseWhereFranchiseNameCnt(String franchiseName);

    int selectFranchiseWhereCitysCnt(List<String> citys);

    int selectFranchiseCnt();
}
