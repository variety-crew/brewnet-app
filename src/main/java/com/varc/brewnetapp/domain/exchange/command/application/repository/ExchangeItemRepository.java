package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ExchangeItemRepositoryCommand")
public interface ExchangeItemRepository extends JpaRepository<ExchangeItem, Integer> {

    List<ExchangeItem> findByExchangeItemCode_ExchangeCode(int exchangeCode);
}
