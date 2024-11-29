package com.varc.brewnetapp.domain.item.command.domain.repository;

import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {


}
