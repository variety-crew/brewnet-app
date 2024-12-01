package com.varc.brewnetapp.domain.order.query.service;

import com.varc.brewnetapp.common.SearchCriteria;
import com.varc.brewnetapp.domain.member.query.service.MemberService;
import com.varc.brewnetapp.domain.order.query.dto.*;
import com.varc.brewnetapp.domain.order.query.mapper.OrderCounterMapper;
import com.varc.brewnetapp.domain.order.query.mapper.OrderMapper;
import com.varc.brewnetapp.exception.InvalidCriteriaException;
import com.varc.brewnetapp.exception.NoAccessAuthoritiesException;
import com.varc.brewnetapp.exception.OrderNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderCounterMapper orderCounterMapper;
    private final OrderMapper orderMapper;
    private final OrderValidateService orderValidateService;
    private final MemberService memberService;

    @Autowired
    public OrderQueryServiceImpl(
            OrderCounterMapper orderCounterMapper,
            OrderMapper orderMapper,
            OrderValidateService orderValidateService,
            MemberService memberService
    ) {
        this.orderCounterMapper = orderCounterMapper;
        this.orderMapper = orderMapper;
        this.orderValidateService = orderValidateService;
        this.memberService = memberService;
    }

    // for HQ
    @Override
    @Transactional
    public Page<HQOrderDTO> getOrderListForHQ(
            Pageable pageable,
            String filter,
            String sort,
            String startDate,
            String endDate
    ) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        // TODO: check if filter value is one of ["UNCONFIRMED", null]
        // TODO: check if sort value is one of ["createdAtDesc", "createdAtAsc", "sumPriceDesc", "sumPriceAsc"]
        List<HQOrderDTO> hqOrderDTOList = orderMapper.findOrdersForHQBy(filter, sort, size, offset, startDate, endDate);

        int total = orderCounterMapper.countOrdersForHq(filter, startDate, endDate);
        return new PageImpl<>(hqOrderDTOList, pageable, total);
    }

    @Override
    @Transactional
    public Page<HQOrderDTO> searchOrderListForHQ(
            Pageable pageable,
            String filter,
            String sort,
            String startDate,
            String endDate,
            SearchCriteria criteria,
            String keyword
    ) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        if (criteria == null) {
            criteria = SearchCriteria.valueOf("ALL");
        }

        switch (criteria) {
            case ORDER_CODE -> {
                return new PageImpl<>(
                        orderMapper.searchOrdersForHQByOrderCode(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate,
                                keyword
                        ),
                        pageable,
                        orderCounterMapper.countSearchedOrdersForHQByOrderCode(
                                filter,
                                startDate,
                                endDate,
                                keyword
                        )
                );
            }
            case ORDERED_FRANCHISE_NAME -> {
                return new PageImpl<>(
                        orderMapper.searchOrdersForHQByOrderedFranchiseName(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate,
                                keyword
                        ),
                        pageable,
                        orderCounterMapper.countSearchedOrdersForHQByOrderedFranchiseName(
                                filter,
                                startDate,
                                endDate,
                                keyword
                        )
                );
            }
            case ORDER_MANAGER -> {
                return new PageImpl<>(
                        orderMapper.searchOrdersForHQByOrderManager(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate,
                                keyword
                        ),
                        pageable,
                        orderCounterMapper.countSearchedOrdersForHQByOrderManager(
                                filter,
                                startDate,
                                endDate,
                                keyword
                        )
                );
            }
            case ALL -> {
                return new PageImpl<>(
                        orderMapper.findOrdersForHQBy(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate
                        ),
                        pageable,
                        orderCounterMapper.countOrdersForHq(
                                filter,
                                startDate,
                                endDate
                        )
                );
            }
            default -> throw new InvalidCriteriaException(
                    "Invalid Order Criteria. " +
                            "entered Criteria: " + criteria + ". " +
                            " entered keyword: " + keyword + "."
            );
        }
    }

    @Override
    @Transactional
    public List<HQOrderDTO> getExcelDataForHQBy(
            String startDate,
            String endDate,
            SearchCriteria criteria,
            String keyword
    ) {
        if (criteria == null) {
            criteria = SearchCriteria.valueOf("ALL");
        }

        switch (criteria) {
            case ORDER_CODE -> {
                return orderMapper.searchOrdersForHQByOrderCode(
                        null,
                        null,
                        0,
                        0,
                        startDate,
                        endDate,
                        keyword
                );
            }
            case ORDERED_FRANCHISE_NAME -> {
                return orderMapper.searchOrdersForHQByOrderedFranchiseName(
                        null,
                        null,
                        0,
                        0,
                        startDate,
                        endDate,
                        keyword
                );
            }
            case ORDER_MANAGER -> {
                return orderMapper.searchOrdersForHQByOrderManager(
                        null,
                        null,
                        0,
                        0,
                        startDate,
                        endDate,
                        keyword
                );
            }
            case ALL -> {
                return orderMapper.findOrdersForHQBy(
                        null,
                        null,
                        0,
                        0,
                        startDate,
                        endDate
                );
            }
            default -> throw new InvalidCriteriaException(
                    "Invalid Order Criteria. " +
                            "entered Criteria: " + criteria + ". " +
                            " entered keyword: " + keyword + "."
            );
        }
    }

    @Override
    @Transactional
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
    @Transactional
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

        return new PageImpl<>(
                orderMapper.findOrdersForFranchise(
                        filter,
                        sort,
                        size,
                        offset,
                        startDate,
                        endDate,
                        franchiseCode
                ),
                pageable,
                orderCounterMapper.countOrdersForFranchise(
                        filter,
                        franchiseCode,
                        startDate,
                        endDate
                )
        );
    }

    @Override
    @Transactional
    public Page<FranchiseOrderDTO> searchOrderListForFranchise(
            Pageable pageable,
            String filter,
            String sort,
            String startDate,
            String endDate,
            int franchiseCode,
            SearchCriteria criteria,
            String keyword
    ) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        if (criteria == null) {
            criteria = SearchCriteria.valueOf("ALL");
        }

        switch (criteria) {
            case ORDER_CODE -> {
                return new PageImpl<>(
                        orderMapper.searchOrdersForFranchiseByOrderCode(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate,
                                franchiseCode,
                                keyword
                        ),
                        pageable,
                        orderCounterMapper.countSearchedOrdersForFranchiseByOrderCode(
                                filter,
                                startDate,
                                endDate,
                                franchiseCode,
                                keyword
                        )
                );
            }
            case ITEM_NAME -> {
                return new PageImpl<>(
                        orderMapper.searchOrdersForFranchiseByItemName(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate,
                                franchiseCode,
                                keyword
                        ),
                        pageable,
                        orderCounterMapper.countSearchedOrdersForFranchiseByItemName(
                                filter,
                                startDate,
                                endDate,
                                franchiseCode,
                                keyword
                        )
                );
            }
            case ALL -> {
                return new PageImpl<>(
                        orderMapper.findOrdersForFranchise(
                                filter,
                                sort,
                                size,
                                offset,
                                startDate,
                                endDate,
                                franchiseCode
                        ),
                        pageable,
                        orderCounterMapper.countOrdersForFranchise(
                                filter,
                                franchiseCode,
                                startDate,
                                endDate
                        )
                );
            }
            default -> throw new InvalidCriteriaException(
                    "Invalid Order Criteria. " +
                            "entered Criteria: " + criteria + ". " +
                            " entered keyword: " + keyword + "."
            );
        }
    }

    @Override
    public List<FranchiseOrderDTO> getExcelDataForFranchiseBy(
            String startDate,
            String endDate,
            int franchiseCode,
            SearchCriteria criteria,
            String keyword
    ) {
        if (criteria == null) {
            criteria = SearchCriteria.valueOf("ALL");
        }

        switch (criteria) {
            case ORDER_CODE -> {
                return orderMapper.searchOrdersForFranchiseByOrderCode(
                            null,
                            null,
                            0,
                            0,
                            startDate,
                            endDate,
                            franchiseCode,
                            keyword
                    );
            }
            case ITEM_NAME -> {
                return orderMapper.searchOrdersForFranchiseByItemName(
                        null,
                        null,
                        0,
                        0,
                        startDate,
                        endDate,
                        franchiseCode,
                        keyword
                );
            }
            case ALL -> {
                return orderMapper.findOrdersForFranchise(
                        null,
                        null,
                        0,
                        0,
                        startDate,
                        endDate,
                        franchiseCode
                );
            }
            default -> throw new InvalidCriteriaException(
                    "Invalid Order Criteria. " +
                            "entered Criteria: " + criteria + ". " +
                            " entered keyword: " + keyword + "."
            );
        }
    }

    @Override
    @Transactional
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


    // for common
    // 해당 주문의 모든 상태 변경 내역 조회
    @Override
    @Transactional
    public List<OrderStatusHistory> getOrderHistoryByOrderCode(int orderId) {
        return orderMapper.findOrderHistoriesByOrderId(orderId);
    }

    // 해당 주문의 최신 상태 조회
    @Override
    @Transactional
    public OrderStatusHistory getOrderStatusHistoryByOrderCode(int orderCode) {
        return orderMapper.findRecentHistoryByOrderId(orderCode);
    }

    // 해당 주문의 결재 히스토리 조회
    @Override
    public List<OrderApprovalHistoryDTO> getOrderApprovalHistories(Integer orderCode) {
        return orderMapper.findOrderApprovalHistoriesBy(orderCode);
    }

    @Transactional
    public int getFranchiseCodeByLoginId(String loginId) {
        return memberService.getFranchiseInfoByLoginId(loginId).getFranchiseCode();
    }
}
