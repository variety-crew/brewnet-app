package com.varc.brewnetapp.domain.order.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderValidateMapper {
    boolean checkIsOrderFrom(
            @Param("franchiseCode") int franchiseCode,
            @Param("orderCode") int orderCode
    );

    boolean checkIsOrderItemMoreThenOne(
            @Param("orderCode") int orderCode
    );
}
