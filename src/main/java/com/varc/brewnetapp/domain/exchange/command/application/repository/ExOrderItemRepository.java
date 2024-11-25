package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrderItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrderItemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ExOrderItemRepositoryCommand")
public interface ExOrderItemRepository extends JpaRepository<ExOrderItem, ExOrderItemCode> {
    Optional<ExOrderItem> findByOrderItemCode(ExOrderItemCode exOrderItemCode);
    Optional<List<ExOrderItem>> findByOrderItemCode_orderCode(int orderCode);
}

