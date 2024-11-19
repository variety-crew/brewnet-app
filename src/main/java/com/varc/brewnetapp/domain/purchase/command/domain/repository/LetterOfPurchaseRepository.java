package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.LetterOfPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterOfPurchaseRepository extends JpaRepository<LetterOfPurchase, Integer> {

    LetterOfPurchase findByLetterOfPurchaseCodeAndActiveTrue(int purchaseCode);
}
