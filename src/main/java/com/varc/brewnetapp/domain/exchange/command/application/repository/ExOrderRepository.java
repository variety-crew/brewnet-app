package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("OrderRepositoryCommand")
public interface ExOrderRepository extends JpaRepository<ExOrder, Integer> {
    Optional<ExOrder> findById(int orderCode);
}
