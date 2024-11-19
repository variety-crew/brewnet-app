package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("ItemRepositoryCommand")
public interface ItemRepository extends JpaRepository<ExItem, Integer> {
    Optional<ExItem> findById(int orderCode);
}
