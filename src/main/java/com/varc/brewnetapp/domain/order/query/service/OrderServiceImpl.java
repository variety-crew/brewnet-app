package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import com.varc.brewnetapp.domain.order.query.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "queryOrderService")
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // for HQ
    @Override
    public Page<OrderDTO> getOrderListForHQ(Pageable pageable, String filter, String sort) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;
        List<OrderDTO> orders = orderMapper.findOrdersBy(filter, sort, size, offset);
        int total = orderMapper.countOrders(filter);
        return new PageImpl<>(orders, pageable, total);
    }

    // for franchise
    @Override
    public Page<OrderDTO> getOrderListForFranchise(Pageable pageable, String filter, String sort) {
        return null;
    }
}
