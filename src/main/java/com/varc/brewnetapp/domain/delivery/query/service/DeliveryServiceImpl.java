package com.varc.brewnetapp.domain.delivery.query.service;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.mapper.DeliveryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("queryDeliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryMapper deliveryMapper;

    @Autowired
    public DeliveryServiceImpl(DeliveryMapper deliveryMapper) {
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    @Transactional
    public Page<DeliveryDTO> findDeliveryList(DeliveryKind deliveryKind) {

        List<DeliveryDTO> deliveryList = null;
        if(deliveryKind.equals(DeliveryKind.ORDER))
            deliveryList = deliveryMapper.selectOrderDeliveryList();
        else if(deliveryKind.equals(DeliveryKind.EXCHANGE) || deliveryKind.equals(DeliveryKind.RETURN))
            deliveryList = deliveryMapper.selectPickUpDeliveryList();


        return null;
    }
}
