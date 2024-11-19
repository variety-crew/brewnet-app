package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseMember;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchasePosition;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.Stock;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.StockId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseMemberRepository extends JpaRepository<PurchaseMember, Integer> {

    PurchaseMember findByPurchasePositionAndActiveTrue(PurchasePosition purchasePosition);
}
