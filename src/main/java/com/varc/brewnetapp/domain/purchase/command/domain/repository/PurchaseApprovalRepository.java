package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseApproval;
import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseApprovalRepository extends JpaRepository<PurchaseApproval, Integer> {
    PurchaseApproval findByKindAndActiveTrue(KindOfApproval kind);
}
