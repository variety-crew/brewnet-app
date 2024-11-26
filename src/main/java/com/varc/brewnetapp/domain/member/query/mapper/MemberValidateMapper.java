package com.varc.brewnetapp.domain.member.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberValidateMapper {
    boolean existMemberCodeInFranchise(
            @Param("memberCode") int memberCode,
            @Param("franchiseCode") int franchiseCode
    );
}
