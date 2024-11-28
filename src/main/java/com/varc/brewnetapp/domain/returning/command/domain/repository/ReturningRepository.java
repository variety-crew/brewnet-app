package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.Exchange;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.Returning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ReturningRepositoryCommand")
public interface ReturningRepository  extends JpaRepository<Returning, Integer> {
}