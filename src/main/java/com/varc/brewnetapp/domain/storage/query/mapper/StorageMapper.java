package com.varc.brewnetapp.domain.storage.query.mapper;

import com.varc.brewnetapp.domain.storage.common.SearchStorageCriteria;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDetailDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageNameDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StorageMapper {

    List<StorageDTO> searchStorage(SearchStorageCriteria criteria);

    int getTotalStorageCount(SearchStorageCriteria criteria);

    StorageDetailDTO selectStorageByStorageCode(int storageCode);

    List<StorageNameDTO> selectStorageNameList();
}
