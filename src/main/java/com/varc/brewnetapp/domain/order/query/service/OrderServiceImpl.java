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

    @Override
    public List<OrderResponseDTO> getAllOrderListByHqRequest() {
        return List.of();
    }

    @Override
    public OrderResponseDTO getOrderDetailByHqWith(Integer orderCode) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> searchOrderListWithOrderCodeByHqRequest(int orderCode) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> searchOrderListWithFranchiseNameByHqRequest(String franchiseName) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> searchOrderListWithDrafterNameByHqRequest(String drafterName) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrderListByFranchiseRequest() {
        return List.of();
    }

    @Override
    public OrderResponseDTO getOrderDetailByFranchiseWith(Integer orderCode) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> searchOrderListByFranchiseWithOrderCode(int orderCode) {
        return List.of();
    }
}
