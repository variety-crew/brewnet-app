package com.varc.brewnetapp.domain.order.query.mapper;

import com.varc.brewnetapp.domain.order.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

// default: 주문일 역순(최신순)
// 주문일 순
// 주문 금액 높은 순
// 주문 금액 낮은 순

    // for HQ
    List<HQOrderDTO> findOrdersForHQBy(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    OrderDetailForHQDTO findOrderDetailForHqBy(int orderCode);

    // search
    List<HQOrderDTO> searchOrdersForHQByOrderCode(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("keyword") String keyword
    );

    // for FRANCHISE
    List<FranchiseOrderDTO> findOrdersForFranchise(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("franchiseCode") int franchiseCode
    );
    OrderDetailForFranchiseDTO findOrderDetailForFranchiseBy(int orderCode);

    // search
    List<FranchiseOrderDTO> searchOrdersForFranchiseByOrderCode(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("franchiseCode") int franchiseCode,
            @Param("keyword") String keyword
    );

    List<FranchiseOrderDTO> searchOrdersForFranchiseByItemName(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("franchiseCode") int franchiseCode,
            @Param("keyword") String keyword
    );

    // common
    List<OrderApprovalHistoryDTO> findOrderApprovalHistoriesBy(int orderCode);
    OrderStatusHistory findRecentHistoryByOrderId(
            @Param("orderCode") int orderCode
    );
    List<OrderStatusHistory> findOrderHistoriesByOrderId(
            @Param("orderId") int orderId
    );

}
