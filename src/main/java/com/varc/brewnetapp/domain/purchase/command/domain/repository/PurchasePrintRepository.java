package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchasePrint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasePrintRepository extends JpaRepository<PurchasePrint, Integer> {
}
