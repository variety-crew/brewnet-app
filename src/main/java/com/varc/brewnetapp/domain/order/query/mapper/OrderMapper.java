package com.varc.brewnetapp.domain.order.query.mapper;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import com.varc.brewnetapp.domain.order.query.dto.OrderStatusHistory;
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
    List<OrderDTO> findOrdersBy(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset
    );

    // common
    int countOrders(
            @Param("filter") String filter
    );
    List<OrderStatusHistory> findOrderHistoriesByOrderId(int orderId);


    // for HQ
    List<OrderDTO> findOrdersForHQBy(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );


    // for FRANCHISE
    List<OrderDTO> findOrdersForFranchise(
            @Param("filter") String filter,
            @Param("sort") String sort,
            @Param("size") int size,
            @Param("offset") int offset
    );

}
