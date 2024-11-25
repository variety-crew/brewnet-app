package com.varc.brewnetapp.domain.order.command.domain.repository;

import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.OrderApprover;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.compositionkey.OrderApprovalCode;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderApprovalRepository extends JpaRepository<OrderApprover, OrderApprovalCode> {

}
