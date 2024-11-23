package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Integer> {

    PurchaseItem findByItemCodeAndActiveTrue(int itemCode);

    List<PurchaseItem> findByActiveTrue();
}
