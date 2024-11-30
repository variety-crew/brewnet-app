package com.varc.brewnetapp.domain.correspondent.command.domain.repository;

import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.Correspondent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrespondentRepository extends JpaRepository<Correspondent, Integer> {

    Correspondent findByNameAndActiveTrue(String correspondentName);
}
