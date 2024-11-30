package com.varc.brewnetapp.domain.statistics.query.mapper;

import com.varc.brewnetapp.domain.statistics.query.dto.MyWaitApprovalDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderItemStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.SafeStockStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.newOrderDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsMapper {

    OrderStatisticsDTO selectOrderStatistics(String startDate, String endDate);

    List<OrderCountPriceDTO> selectOrderCountPriceList(String yearMonth);

    List<OrderItemStatisticsDTO> selectOrderItemStatistics(String startDate, String endDate);

    int selectOrderItemStatisticsCnt(String startDate, String endDate);

    List<SafeStockStatisticsDTO> selectSafeStock(long offset, long pageSize);

    int selectUnApprovedItemCount(int itemCode);

    int selectSafeStockCnt();

    List<newOrderDTO> selectNewOrder(long offset, long pageSize);

    int selectNewOrderCnt();

    List<MyWaitApprovalDTO> selectApprovalList(long pageSize, long offset, Integer memberCode);

    int selectApprovalListCnt(Integer memberCode);
}
