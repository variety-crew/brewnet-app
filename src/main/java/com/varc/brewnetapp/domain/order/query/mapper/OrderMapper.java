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

    // test
    List<HQOrderDTO> findOrdersBy(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset
    );

    // common
    List<OrderStatusHistory> findOrderHistoriesByOrderId(
            @Param("orderId") int orderId
    );


    // for HQ
    List<HQOrderDTO> findOrdersForHQBy(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    int countOrdersForHq(
            @Param("filter") String filter,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    OrderDetailForHQDTO findOrderDetailForHqBy(int orderCode);


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

    int countOrdersForFranchise(
            @Param("filter") String filter,
            @Param("franchiseCode") int franchiseCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    OrderDetailForFranchiseDTO findOrderDetailForFranchiseBy(int orderCode);

    List<OrderApprovalHistoryDTO> findOrderApprovalHistoriesBy(int orderCode);
}
