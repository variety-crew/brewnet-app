package com.varc.brewnetapp.domain.delivery.command.application.service;

import com.varc.brewnetapp.domain.delivery.command.application.dto.CreateDeliveryStatusRequestDTO;

public interface DeliveryService {

    void createDeliveryStatus(CreateDeliveryStatusRequestDTO createDeliveryStatusRequestDTO);
}
