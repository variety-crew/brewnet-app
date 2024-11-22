package com.varc.brewnetapp.domain.storage.query.service;

import com.varc.brewnetapp.domain.storage.common.PageResponse;
import com.varc.brewnetapp.domain.storage.common.SearchItemStockCriteria;
import com.varc.brewnetapp.domain.storage.common.SearchStorageCriteria;
import com.varc.brewnetapp.domain.storage.query.dto.StockDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDetailDTO;
import com.varc.brewnetapp.domain.storage.query.dto.StorageNameDTO;
import com.varc.brewnetapp.domain.storage.query.mapper.StorageMapper;
import com.varc.brewnetapp.exception.StorageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("StorageServiceQuery")
public class StorageServiceImpl implements StorageService {

    private final StorageMapper storageMapper;

    @Autowired
    public StorageServiceImpl(StorageMapper storageMapper) {
        this.storageMapper = storageMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<StorageDTO>> selectStorage(String loginId, String storageName,
                                                        int pageNumber, int pageSize) {

        SearchStorageCriteria criteria = new SearchStorageCriteria();
        criteria.setStorageName(storageName);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        List<StorageDTO> storages = storageMapper.searchStorage(criteria);
        int totalCount = storageMapper.getTotalStorageCount(criteria);
        PageResponse<List<StorageDTO>> response = new PageResponse<>(storages, pageNumber, pageSize, totalCount);

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public StorageDetailDTO selectOneStorage(String loginId, int storageCode) {

        StorageDetailDTO storage = storageMapper.selectStorageByStorageCode(storageCode);

        if (storage == null) throw new StorageNotFoundException("존재하지 않는 창고입니다.");
        if (!storage.isActive()) throw new StorageNotFoundException("삭제된 창고입니다.");

        return storage;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StorageNameDTO> selectStorageList(String loginId) {

        List<StorageNameDTO> storageNames = storageMapper.selectStorageNameList();

        if (storageNames == null) throw new StorageNotFoundException("창고가 존재하지 않습니다.");

        return storageNames;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<List<StockDTO>> selectAllStock(
                                String loginId, int storageCode, String itemName, int pageNumber, int pageSize) {

        SearchItemStockCriteria criteria = new SearchItemStockCriteria();
        criteria.setStorageCode(storageCode);
        criteria.setItemName(itemName);
        criteria.setPageNumber(pageNumber);
        criteria.setPageSize(pageSize);

        int offset = (pageNumber - 1) * pageSize;
        criteria.setOffset(offset);

        List<StockDTO> stockList = storageMapper.searchStockOfStorage(criteria);
        int totalCount = storageMapper.getTotalStorageStockCount(criteria);
        PageResponse<List<StockDTO>> response = new PageResponse<>(stockList, pageNumber, pageSize, totalCount);

        return response;
    }
}
