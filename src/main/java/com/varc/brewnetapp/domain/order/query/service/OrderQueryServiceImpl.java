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
@Service
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderMapper orderMapper;

    public OrderQueryServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Page<OrderDTO> getOrderListForTest(Pageable pageable, String filter, String sort) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;
        List<OrderDTO> orders = orderMapper.findOrdersBy(filter, sort, size, offset);
        int total = orderMapper.countOrders(filter);
        return new PageImpl<>(orders, pageable, total);
    }

    // for HQ
    @Override
    public Page<OrderDTO> getOrderListForHQ(Pageable pageable, String filter, String sort) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;
        log.debug("filter: {}", filter);
        log.debug("page: {}, size: {}", page, size);

        // TODO: check if filter value is one of ["UNCONFIRMED", null]
        // TODO: check if sort value is one of ["createdAtDesc", "createdAtAsc", "sumPriceDesc", "sumPriceAsc"]

        List<OrderDTO> orderDTOList = orderMapper.findOrdersForHQBy(filter, sort, size, offset);
        orderDTOList.forEach(
                orderDTO -> log.debug("orderDTO: {}", orderDTO)
        );


        int total = orderMapper.countOrders(filter);
        return new PageImpl<>(orderDTOList, pageable, total);
    }

    // for franchise
    @Override
    public Page<OrderDTO> getOrderListForFranchise(Pageable pageable, String filter, String sort) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        // TODO: get order list query for franchise

        List<OrderDTO> orderDTOList = orderMapper.findOrdersForFranchise(filter, sort, size, offset);

        int total = orderMapper.countOrders(filter);

        return new PageImpl<>(orderDTOList , pageable, total);
    }
}
