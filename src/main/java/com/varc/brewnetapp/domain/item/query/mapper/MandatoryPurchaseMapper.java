package com.varc.brewnetapp.domain.item.query.mapper;

import com.varc.brewnetapp.domain.item.query.dto.MustBuyItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MandatoryPurchaseMapper {
    List<MustBuyItemDTO> getMandatoryPurchaseList();
}
