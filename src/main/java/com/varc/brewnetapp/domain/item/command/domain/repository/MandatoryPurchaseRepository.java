package com.varc.brewnetapp.domain.item.command.domain.repository;

import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.MandatoryPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MandatoryPurchaseRepository extends JpaRepository<MandatoryPurchase, Integer> {
    Optional<MandatoryPurchase> findMandatoryPurchaseByItemCodeAndActiveTrue(int itemCode);
}
