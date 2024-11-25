package com.varc.brewnetapp.domain.order.command.domain.repository;

import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.OrderItem;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderItemCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemCode> {
    List<OrderItem> findByOrderItemCode_OrderCode(int orderCode);
}
