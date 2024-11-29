package com.varc.brewnetapp.domain.order.command.domain.repository;

import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.OrderApprover;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderApprovalCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderApprovalRepository extends JpaRepository<OrderApprover, OrderApprovalCode> {
    List<OrderApprover> findByOrderApprovalCode_OrderCode(int orderApprovalCode);
}
