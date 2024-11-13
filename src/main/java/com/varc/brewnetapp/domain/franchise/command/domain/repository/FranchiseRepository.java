package com.varc.brewnetapp.domain.franchise.command.domain.repository;

import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity.Franchise;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {

    Optional<Franchise> findByFranchiseName(String franchiseName);
}
