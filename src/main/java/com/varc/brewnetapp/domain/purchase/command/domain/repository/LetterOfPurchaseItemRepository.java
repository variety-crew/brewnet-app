package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchaseItem;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchaseItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterOfPurchaseItemRepository extends JpaRepository<LetterOfPurchaseItem, LetterOfPurchaseItemId> {
}
