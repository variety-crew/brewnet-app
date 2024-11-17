package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.order.query.mapper.OrderMapper;
import com.varc.brewnetapp.domain.order.query.vo.hq.response.OrderResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderResponseVO> getAllOrderListByHqRequest() {
        return List.of();
    }

    @Override
    public OrderResponseVO getOrderDetailByHqWith(Integer orderCode) {
        return null;
    }

    @Override
    public List<OrderResponseVO> searchOrderListWithOrderCodeByHqRequest(int orderCode) {
        return List.of();
    }

    @Override
    public List<OrderResponseVO> searchOrderListWithFranchiseNameByHqRequest(String franchiseName) {
        return List.of();
    }

    @Override
    public List<OrderResponseVO> searchOrderListWithDrafterNameByHqRequest(String drafterName) {
        return List.of();
    }

    @Override
    public List<OrderResponseVO> getAllOrderListByFranchiseRequest() {
        return List.of();
    }

    @Override
    public OrderResponseVO getOrderDetailByFranchiseWith(Integer orderCode) {
        return null;
    }

    @Override
    public List<OrderResponseVO> searchOrderListByFranchiseWithOrderCode(int orderCode) {
        return List.of();
    }
}
