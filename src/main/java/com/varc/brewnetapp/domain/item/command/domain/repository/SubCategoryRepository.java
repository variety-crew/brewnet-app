package com.varc.brewnetapp.domain.item.command.domain.repository;

import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.SubCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    Optional<SubCategory> findByName(String categoryName);
}
