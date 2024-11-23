package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.mapper.OrderValidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderValidateServiceImpl implements OrderValidateService {

    private final OrderValidateMapper orderValidateMapper;

    @Autowired
    public OrderValidateServiceImpl(OrderValidateMapper orderValidateMapper) {
        this.orderValidateMapper = orderValidateMapper;
    }

    @Override
    public boolean isOrderFromFranchise(int franchiseCode, int orderCode) {
        return orderValidateMapper.checkIsOrderFrom(franchiseCode, orderCode);
    }
}
