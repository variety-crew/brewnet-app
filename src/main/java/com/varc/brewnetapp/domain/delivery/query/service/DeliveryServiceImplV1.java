package com.varc.brewnetapp.domain.delivery.query.service;

import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryStatus;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDetailDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.ItemDTO;
import com.varc.brewnetapp.domain.delivery.query.mapper.DeliveryMapper;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("queryDeliveryServiceV1")
@Slf4j
@Primary
public class DeliveryServiceImplV1 implements DeliveryService {

    private final DeliveryMapper deliveryMapper;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Autowired
    public DeliveryServiceImplV1(DeliveryMapper deliveryMapper, JwtUtil jwtUtil, MemberRepository memberRepository) {
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
            deliveryList.stream().forEach(deliveryDTO -> {
                deliveryDTO.setDeliveryStatus(DeliveryStatus.START_DELIVERY);
            });
        }
        else if(deliveryKind.equals(DeliveryKind.EXCHANGE) || deliveryKind.equals(DeliveryKind.RETURN)){
            deliveryList = deliveryMapper.selectPickUpDeliveryList(offset, pageSize);
            count = deliveryMapper.selectPickUpDeliveryListCnt();
            deliveryList.stream().forEach(deliveryDTO -> {
                if (deliveryDTO.getDeliveryStatus().equals("APPROVED"))
                    deliveryDTO.setDeliveryStatus(DeliveryStatus.START_PICK);
                else if(deliveryDTO.getDeliveryStatus().equals("PICKED"))
                    deliveryDTO.setDeliveryStatus(DeliveryStatus.START_DELIVERY);
            });
        }

        if(deliveryList == null || deliveryList.size() == 0)
            throw new EmptyDataException("배송 가능한 주문이 없습니다");

        return new PageImpl<>(deliveryList, page, count);
    }

    @Override
    @Transactional
    public DeliveryDetailDTO findDeliveryDetail(String accessToken) {
        long startTime = System.nanoTime();
        
        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));
        Member member = memberRepository.findById(loginId)
            .orElseThrow(() -> new MemberNotFoundException("회원의 토큰이 잘못되었습니다"));

        int deliveryMemberCode = member.getMemberCode();

        DeliveryDetailDTO myDelivery = deliveryMapper.selectMyDeliveryDetailV1(deliveryMemberCode)
            .orElseThrow(() -> new EmptyDataException("배송 가능한 주문이 없습니다"));

        List<ItemDTO> items = null;

        if(myDelivery.getDeliveryKind().equals(DeliveryKind.ORDER))
            items = deliveryMapper.selectOrderDelivery(myDelivery.getCode());
        else if(myDelivery.getDeliveryKind().equals(DeliveryKind.EXCHANGE))
            items = deliveryMapper.selectExchangeDelivery(myDelivery.getCode());
        else if(myDelivery.getDeliveryKind().equals(DeliveryKind.RETURN))
            items = deliveryMapper.selectReturnDelivery(myDelivery.getCode());
        else
            throw new InvalidDataException("배송 가능한 주문이 없습니다");

        myDelivery.setItems(items);

        long endTime = System.nanoTime();
        log.info("V1 내 배송 수행 시간 측정 : " + (endTime - startTime) + "ns");

        return myDelivery;
    }

    @Override
    @Transactional
    public List<Integer> findDeliveryFranchiseMemberCode(int code) {

        List<Integer> franchiseMemberCodeList = deliveryMapper.selectDeliveryFranchiseMemberCode(code);
        
        if(franchiseMemberCodeList.isEmpty())
            throw new EmptyDataException("주문을 한 가맹점의 회원 코드가 없습니다");

        return franchiseMemberCodeList;
    }

}
