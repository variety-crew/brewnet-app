package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("ExchangeItemRepositoryCommand")
public interface ExchangeItemRepository extends JpaRepository<ExchangeItem, Integer> {
}
