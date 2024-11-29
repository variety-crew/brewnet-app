package com.varc.brewnetapp.domain.item.command.domain.repository;

import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.MandatoryPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MandatoryPurchaseRepository extends JpaRepository<MandatoryPurchase, Integer> {
}
