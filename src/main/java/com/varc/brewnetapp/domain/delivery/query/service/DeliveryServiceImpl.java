package com.varc.brewnetapp.domain.delivery.query.service;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.mapper.DeliveryMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<DeliveryDTO> findDeliveryList(DeliveryKind deliveryKind, Pageable page) {

        long pageSize = page.getPageSize();
        long pageNumber = page.getPageNumber();
        long offset = pageNumber * pageSize;

        List<DeliveryDTO> deliveryList = null;
        int count = 0;

        if(deliveryKind.equals(DeliveryKind.ORDER)){
            deliveryList = deliveryMapper.selectOrderDeliveryList(offset, pageSize);
            count = deliveryMapper.selectOrderDeliveryListCnt();

        }
        else if(deliveryKind.equals(DeliveryKind.EXCHANGE) || deliveryKind.equals(DeliveryKind.RETURN)){
            deliveryList = deliveryMapper.selectPickUpDeliveryList(offset, pageSize);
            count = deliveryMapper.selectPickUpDeliveryListCnt();
        }

        if(deliveryList == null || deliveryList.size() == 0)
            throw new EmptyDataException("배송 가능한 주문이 없습니다");

        return new PageImpl<>(deliveryList, page, count);
    }
}
