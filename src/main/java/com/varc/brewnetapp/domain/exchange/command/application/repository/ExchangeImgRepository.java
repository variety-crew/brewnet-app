package com.varc.brewnetapp.domain.exchange.command.application.repository;

import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.ExchangeImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ExchangeImgRepositoryCommand")
public interface ExchangeImgRepository extends JpaRepository<ExchangeImg, Integer> {
}
