package com.varc.brewnetapp.domain.order.query.mapper;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    OrderDTO baseOrderList();
}
