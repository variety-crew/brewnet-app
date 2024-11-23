package com.varc.brewnetapp.domain.item.query.mapper;

import com.varc.brewnetapp.domain.item.query.dto.ItemDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {

    List<ItemDTO> selectItemList(long offset, long pageSize, String itemName, String itemCode, String sort, String categoryCode, String correspondentCode);

    int selectItemListCnt(String itemName, String itemCode, String categoryCode, String correspondentCode);
}
