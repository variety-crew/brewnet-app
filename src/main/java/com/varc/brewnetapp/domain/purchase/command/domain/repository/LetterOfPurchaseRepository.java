package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterOfPurchaseRepository extends JpaRepository<LetterOfPurchase, Integer> {
}
