package com.varc.brewnetapp.domain.storage.command.application.service;

import com.varc.brewnetapp.domain.storage.command.application.dto.StorageRequestDTO;

public interface StorageService {

    void createStorage(String loginId, StorageRequestDTO newStorage);
}
