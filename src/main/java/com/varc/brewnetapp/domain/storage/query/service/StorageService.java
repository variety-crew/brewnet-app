package com.varc.brewnetapp.domain.storage.query.service;

import com.varc.brewnetapp.domain.storage.common.PageResponse;
import com.varc.brewnetapp.domain.storage.query.dto.StockDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDetailDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageNameDTO;

import java.util.List;

public interface StorageService {

    PageResponse<List<StorageDTO>> selectStorage(String loginId, String storageName, int pageNumber, int pageSize);

    StorageDetailDTO selectOneStorage(String loginId, int storageCode);

    List<StorageNameDTO> selectStorageList(String loginId);

    PageResponse<List<StockDTO>> selectAllStock(
                                    String loginId, Integer storageCode, String itemName, int pageNumber, int pageSize);
}
