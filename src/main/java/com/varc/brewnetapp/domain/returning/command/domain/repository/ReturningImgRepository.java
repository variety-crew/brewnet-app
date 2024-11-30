package com.varc.brewnetapp.domain.returning.command.domain.repository;

import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ReturningImgRepositoryCommand")
public interface ReturningImgRepository extends JpaRepository<ReturningImg, Integer> {
}
