package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.mapper.OrderValidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderValidateServiceImpl implements OrderValidateService {

    private final OrderValidateMapper orderValidateMapper;

    @Autowired
    public OrderValidateServiceImpl(OrderValidateMapper orderValidateMapper) {
        this.orderValidateMapper = orderValidateMapper;
    }

    @Transactional
    @Override
    public boolean isOrderFromFranchise(int franchiseCode, int orderCode) {
        return orderValidateMapper.checkIsOrderFrom(franchiseCode, orderCode);
    }

    @Transactional
    @Override
    public boolean isOrderItemsMoreThenOne(int orderCode) {
        return orderValidateMapper.checkIsOrderItemMoreThenOne(orderCode);
    }
}
