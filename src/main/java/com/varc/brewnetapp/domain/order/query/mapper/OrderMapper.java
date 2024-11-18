package com.varc.brewnetapp.domain.order.query.mapper;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderDTO> findOrders();
}
