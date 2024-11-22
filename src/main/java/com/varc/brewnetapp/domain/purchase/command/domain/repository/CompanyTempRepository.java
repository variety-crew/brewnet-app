package com.varc.brewnetapp.domain.purchase.command.domain.repository;

import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.CompanyTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTempRepository extends JpaRepository<CompanyTemp, Integer> {

    CompanyTemp findTopByActiveTrueOrderByCompanyCodeDesc();
}
