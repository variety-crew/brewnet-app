package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseMember;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseStatusHistoryRepository extends JpaRepository<PurchaseStatusHistory, Integer> {
}
