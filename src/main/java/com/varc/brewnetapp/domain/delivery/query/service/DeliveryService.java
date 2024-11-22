package com.varc.brewnetapp.domain.delivery.query.service;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {

    Page<DeliveryDTO> findDeliveryList(DeliveryKind deliveryKind, Pageable page);
}
