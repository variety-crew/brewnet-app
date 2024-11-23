package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.query.dto.*;
import com.varc.brewnetapp.domain.order.query.mapper.OrderMapper;
import com.varc.brewnetapp.exception.NoAccessAuthoritiesException;
import com.varc.brewnetapp.exception.OrderNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderMapper orderMapper;
    private final OrderValidateService orderValidateService;
    private final MemberService memberService;


    public OrderQueryServiceImpl(OrderMapper orderMapper,
                                 OrderValidateService orderValidateService,
                                 MemberService memberService) {
        this.orderMapper = orderMapper;
        this.orderValidateService = orderValidateService;
        this.memberService = memberService;
    }

    @Override
    public Page<HQOrderDTO> getOrderListForTest(Pageable pageable, String filter, String sort) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;
        List<HQOrderDTO> orders = orderMapper.findOrdersBy(filter, sort, size, offset);
        int total = orderMapper.countOrdersForHq(filter);
        return new PageImpl<>(orders, pageable, total);
    }

    // for common
    @Override
    public List<OrderStatusHistory> getOrderHistoryByOrderId(int orderId) {
        return orderMapper.findOrderHistoriesByOrderId(orderId);
    }

    // for HQ
    @Override
    public Page<HQOrderDTO> getOrderListForHQ(Pageable pageable, String filter, String sort, String startDate, String endDate) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;
        log.debug("filter: {}", filter);
        log.debug("page: {}, size: {}", page, size);

        // TODO: check if filter value is one of ["UNCONFIRMED", null]
        // TODO: check if sort value is one of ["createdAtDesc", "createdAtAsc", "sumPriceDesc", "sumPriceAsc"]
        List<HQOrderDTO> hqOrderDTOList = orderMapper.findOrdersForHQBy(filter, sort, size, offset, startDate, endDate);

        int total = orderMapper.countOrdersForHq(filter);
        return new PageImpl<>(hqOrderDTOList, pageable, total);
    }

    @Override
    public Page<HQOrderDTO> searchOrderListForHQ(Pageable pageable, String filter, String criteria) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;
//        List<OrderDTO> searchedList = orderMapper.findOrderListForHQBy(filter, sort, size, offset, criteria);

        int total = orderMapper.countOrdersForHq(filter);
//        return new PageImpl<>(null, pageable, total);
        return null;
    }

    @Override
    public OrderDetailForHQDTO getOrderDetailForHqBy(int orderCode) {
        OrderDetailForHQDTO orderDetail = orderMapper.findOrderDetailForHqBy(orderCode);
        if (orderDetail == null) {
            throw new OrderNotFound("Order not found");
        } else {
            return orderDetail;
        }
    }

    @Override
    public OrderRequestDTO printOrderRequest(int orderCode) {

        // TODO: check is tbl_order.approval_status is 'APPROVED'

        // TODO: get order detail information

        return null;
    }

    // for franchise
    @Override
    public Page<FranchiseOrderDTO> getOrderListForFranchise(
            Pageable pageable,
            String filter,
            String sort,
            String startDate,
            String endDate,
            int franchiseCode
    ) {

        // TODO: check if franchise valid

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        // TODO: get order list query for franchise

        List<FranchiseOrderDTO> franchiseOrderDTO = orderMapper.findOrdersForFranchise(
                filter, sort, size, offset, startDate, endDate, franchiseCode
        );

        int total = orderMapper.countOrdersForFranchise(filter, franchiseCode);

        return new PageImpl<>(franchiseOrderDTO, pageable, total);
    }

    @Override
    public OrderDetailForFranchiseDTO getOrderDetailForFranchiseBy(int orderCode, String loginId) {
        int franchiseCode = getFranchiseCodeByLoginId(loginId);

        boolean isOrderFromFranchise = orderValidateService.isOrderFromFranchise(
                franchiseCode,
                orderCode
        );

        if (isOrderFromFranchise) {
            return orderMapper.findOrderDetailForFranchiseBy(orderCode);
        } else {
            throw new NoAccessAuthoritiesException("No Authorization for order " + orderCode + ", franchiseCode: " + franchiseCode);
        }
    }

    private int getFranchiseCodeByLoginId(String loginId) {
        return memberService.getFranchiseInfoByLoginId(loginId).getFranchiseCode();
    }
}
