package com.varc.brewnetapp.domain.statistics.query.mapper;

import com.varc.brewnetapp.domain.statistics.query.dto.MyWaitApprovalDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderItemStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.SafeStockStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.NewOrderDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsMapper {

    OrderStatisticsDTO selectOrderStatistics(String startDate, String endDate);

    List<OrderCountPriceDTO> selectOrderCountPriceList(String yearMonth);

    List<OrderItemStatisticsDTO> selectOrderItemStatistics(String startDate, String endDate);

    Integer selectOrderItemStatisticsCnt(String startDate, String endDate);

    List<SafeStockStatisticsDTO> selectSafeStock(long offset, long pageSize);

    Integer selectUnApprovedItemCount(int itemCode);

    Integer selectSafeStockCnt();

    List<NewOrderDTO> selectNewOrder(long offset, long pageSize);

    Integer selectNewOrderCnt();

    List<MyWaitApprovalDTO> selectApprovalList(long pageSize, long offset, Integer memberCode);

    Integer selectApprovalListCnt(Integer memberCode);
}
