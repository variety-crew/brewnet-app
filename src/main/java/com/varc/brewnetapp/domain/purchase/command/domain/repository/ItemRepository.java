package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.Item;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.Stock;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.StockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
