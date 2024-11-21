package com.varc.brewnetapp.domain.storage.query.service;

import com.varc.brewnetapp.domain.storage.common.PageResponse;
import com.varc.brewnetapp.domain.storage.common.SearchStorageCriteria;
import com.varc.brewnetapp.domain.storage.query.dto.StorageDTO;
import com.varc.brewnetapp.domain.storage.query.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StorageServiceQuery")
public class StorageServiceImpl implements StorageService {

    private final StorageMapper storageMapper;

    @Autowired
    public StorageServiceImpl(StorageMapper storageMapper) {
        this.storageMapper = storageMapper;
    }

    @Override
    public PageResponse<List<StorageDTO>> selectStorage(String loginId, String storageName, int pageNumber, int pageSize) {

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
}
