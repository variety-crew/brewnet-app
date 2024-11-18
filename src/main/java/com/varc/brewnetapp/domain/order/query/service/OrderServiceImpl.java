package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.dto.OrderResponseDTO;
import com.varc.brewnetapp.domain.order.query.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "queryOrderService")
public class OrderServiceImpl implements OrderService{
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // by hq

    @Override
    public List<OrderResponseDTO> getAllOrderListBy() {
        orderMapper.findOrders();
        return List.of();
    }


    // by franchise
}
