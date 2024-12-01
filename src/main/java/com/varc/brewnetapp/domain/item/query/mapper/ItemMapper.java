package com.varc.brewnetapp.domain.item.query.mapper;

import com.varc.brewnetapp.domain.item.query.dto.ItemCategoryDTO;
import com.varc.brewnetapp.domain.item.query.dto.ItemDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {

    List<ItemDTO> selectItemList(long offset, long pageSize, String itemName, String itemCode, String sort, String categoryCode, String correspondentCode);

    int selectItemListCnt(String itemName, String itemCode, String categoryCode, String correspondentCode);

    int findItemPriceById(int itemCode);

    List<ItemDTO> selectHqItemList(long offset, long pageSize, String itemName, String itemCode, String sort, String categoryCode, String correspondentCode);

    int selectHqItemListCnt(String itemName, String itemCode, String categoryCode, String correspondentCode);

    List<ItemCategoryDTO> selectItemListWhereCategoryCode(int subCategoryCode);

    ItemDTO selectItem(int itemCode);
}
