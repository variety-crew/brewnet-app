package com.varc.brewnetapp.domain.delivery.command.domain.repository;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryReturnRepository extends JpaRepository<DeliveryReturn, Integer> {

}
