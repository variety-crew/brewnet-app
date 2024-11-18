package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import com.varc.brewnetapp.domain.order.query.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "queryOrderService")
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderMapper orderMapper,
                            ModelMapper modelMapper) {
        this.orderMapper = orderMapper;
        this.modelMapper = modelMapper;
    }

    // by hq
    @Override
    public List<OrderDTO> getAllOrderListBy() {
        List<OrderDTO> allOrderList = orderMapper.findOrders();
        log.debug("allOrderList: {}", allOrderList);
        return allOrderList;
    }


    // by franchise
}
