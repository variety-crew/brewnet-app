package com.varc.brewnetapp.domain.statistics.query.mapper;

import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderItemStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsMapper {

    OrderStatisticsDTO selectOrderStatistics(String startDate, String endDate);

    List<OrderCountPriceDTO> selectOrderCountPriceList(String yearMonth);

    List<OrderItemStatisticsDTO> selectOrderItemStatistics(String startDate, String endDate);

    int selectOrderItemStatisticsCnt(String startDate, String endDate);
}
