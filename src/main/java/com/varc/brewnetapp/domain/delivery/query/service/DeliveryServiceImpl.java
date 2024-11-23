package com.varc.brewnetapp.domain.delivery.query.service;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDetailDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.ItemDTO;
import com.varc.brewnetapp.domain.delivery.query.mapper.DeliveryMapper;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
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
    private final MemberRepository memberRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryMapper deliveryMapper, JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.deliveryMapper = deliveryMapper;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
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
        Member member = memberRepository.findById(loginId)
            .orElseThrow(() -> new MemberNotFoundException("회원의 토큰이 잘못되었습니다"));

        int deliveryMemberCode = member.getMemberCode();

        DeliveryDetailDTO myDelivery = deliveryMapper.selectMyDeliveryDetail(deliveryMemberCode)
            .orElseThrow(() -> new EmptyDataException("배송 가능한 주문이 없습니다"));

        List<ItemDTO> items = null;

        if(myDelivery.getDeliveryKind().equals(DeliveryKind.ORDER))
            items = deliveryMapper.selectOrderDelivery(myDelivery.getCode());
        else if(myDelivery.getDeliveryKind().equals(DeliveryKind.EXCHANGE))
            items = deliveryMapper.selectExchangeDelivery(myDelivery.getCode());
        else if(myDelivery.getDeliveryKind().equals(DeliveryKind.RETURN))
            items = deliveryMapper.selectReturnDelivery(myDelivery.getCode());

        myDelivery.setItems(items);

        return myDelivery;
    }
}
