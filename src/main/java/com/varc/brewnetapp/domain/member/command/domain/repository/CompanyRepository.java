package com.varc.brewnetapp.domain.member.command.domain.repository;

import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
