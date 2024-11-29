package com.varc.brewnetapp.domain.statistics.query.service;

import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.SafeStockStatisticsDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface StatisticsService {

    OrderStatisticsDTO findOrderStatistics(String startDate, String endDate);

    List<OrderCountPriceDTO> findOrderCountPriceStatistics(String yearMonth);

    List<SafeStockStatisticsDTO> findSafeStock(Pageable page);
}
