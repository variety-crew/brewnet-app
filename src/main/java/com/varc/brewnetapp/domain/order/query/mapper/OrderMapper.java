package com.varc.brewnetapp.domain.order.query.mapper;

import com.varc.brewnetapp.domain.order.query.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    // for HQ

// default: 주문일 역순(최신순)
// 주문일 순
// 주문 금액 높은 순
// 주문 금액 낮은 순

    List<OrderDTO> findOrdersBy(@Param("filter") String filter,
                              @Param("sort") String sort,
                              @Param("size") int size,
                              @Param("offset") int offset);

    List<OrderDTO> findOrdersForHQ(@Param("filter") String filter,
                                                  @Param("sort") String sort,
                                                  @Param("size") int size,
                                                  @Param("offset") int offset);

    // for FRANCHISE
    // TODO: read order list for franchise
    default List<OrderDTO> findOrdersForFranchise(@Param("filter") String filter,
                                   @Param("sort") String sort,
                                   @Param("size") int size,
                                   @Param("offset") int offset) {
        return null;
    }


    // common
    int countOrders(@Param("filter") String filter);
}
