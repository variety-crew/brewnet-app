package com.varc.brewnetapp.domain.delivery.query.service;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDetailDTO;
import com.varc.brewnetapp.domain.delivery.query.mapper.DeliveryMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.security.utility.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Autowired
    public DeliveryServiceImpl(DeliveryMapper deliveryMapper, JwtUtil jwtUtil) {
        this.deliveryMapper = deliveryMapper;
        this.jwtUtil = jwtUtil;
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

    @Override
    @Transactional
    public DeliveryDetailDTO findDeliveryDetail(String accessToken) {

        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));

        DeliveryDetailDTO myDelivery = deliveryMapper.selectMyDeliveryDetail(loginId)
            .orElseThrow(() -> new EmptyDataException("배송 가능한 주문이 없습니다"));

        DeliveryDetailDTO deliveryDetail = null;

        if(myDelivery.getDeliveryKind().equals(DeliveryKind.ORDER))
            deliveryDetail = deliveryMapper.selectOrderDelivery(myDelivery.getCode());
        else if(myDelivery.getDeliveryKind().equals(DeliveryKind.EXCHANGE))
            deliveryDetail = deliveryMapper.selectExchangeDelivery(myDelivery.getCode());
        else if(myDelivery.getDeliveryKind().equals(DeliveryKind.RETURN))
            deliveryDetail = deliveryMapper.selectReturnDelivery(myDelivery.getCode());

        deliveryDetail.setDeliveryKind(myDelivery.getDeliveryKind());
        deliveryDetail.setDeliveryStatus(myDelivery.getDeliveryStatus());
        deliveryDetail.setCode(myDelivery.getCode());

        return null;
    }
}
