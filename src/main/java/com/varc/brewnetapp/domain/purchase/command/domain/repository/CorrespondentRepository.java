package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.Correspondent;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrespondentRepository extends JpaRepository<Correspondent, Integer> {
}
