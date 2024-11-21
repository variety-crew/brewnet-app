package com.varc.brewnetapp.domain.franchise.query.mapper;

import com.varc.brewnetapp.domain.franchise.query.dto.FranchiseDTO;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FranchiseMapper {

    Optional<FranchiseDTO> selectFranchise(Integer franchiseCode);
}
