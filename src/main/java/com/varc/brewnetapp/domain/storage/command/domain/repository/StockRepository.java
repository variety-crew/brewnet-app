package com.varc.brewnetapp.domain.storage.command.domain.repository;

import com.varc.brewnetapp.domain.storage.command.domain.aggregate.Stock;
import com.varc.brewnetapp.domain.storage.command.domain.aggregate.StockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, StockId> {

    Stock findByStorageCodeAndItemCode(Integer storageCode, Integer itemCode);
}
