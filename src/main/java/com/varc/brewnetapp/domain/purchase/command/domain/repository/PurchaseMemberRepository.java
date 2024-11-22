package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseMember;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchasePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseMemberRepository extends JpaRepository<PurchaseMember, Integer> {

    PurchaseMember findByPurchasePositionAndActiveTrue(PurchasePosition purchasePosition);
}
