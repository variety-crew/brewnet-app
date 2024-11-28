package com.varc.brewnetapp.domain.order.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderCounterMapper {

    // for HQ
    int countOrdersForHq(
            @Param("filter") String filter,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    int countSearchedOrdersForHQByOrderCode(
            @Param("filter") String filter,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("keyword") String keyword
    );
    int countSearchedOrdersForHQByOrderedFranchiseName(
            @Param("filter") String filter,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("keyword") String keyword
    );

    // for FRANCHISE
    int countOrdersForFranchise(
            @Param("filter") String filter,
            @Param("franchiseCode") int franchiseCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    int countSearchedOrdersForFranchiseByOrderCode(
            @Param("filter") String filter,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("franchiseCode") int franchiseCode,
            @Param("keyword") String keyword
    );
    int countSearchedOrdersForFranchiseByItemName(
            @Param("filter") String filter,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("franchiseCode") int franchiseCode,
            @Param("keyword") String keyword
    );

}
