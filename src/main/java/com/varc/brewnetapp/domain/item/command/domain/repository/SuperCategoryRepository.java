package com.varc.brewnetapp.domain.item.command.domain.repository;

import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.SuperCategory;
import java.util.Optional;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperCategoryRepository extends JpaRepository<SuperCategory, Integer> {

    Optional<SuperCategory> findByName(String categoryName);
}
