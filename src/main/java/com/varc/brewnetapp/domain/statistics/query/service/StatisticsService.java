package com.varc.brewnetapp.domain.statistics.query.service;

import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import java.util.List;

public interface StatisticsService {

    OrderStatisticsDTO findOrderStatistics(String startDate, String endDate);

    List<OrderCountPriceDTO> findOrderCountPriceStatistics(String yearMonth);
}
