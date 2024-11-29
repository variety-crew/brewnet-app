package com.varc.brewnetapp.domain.statistics.query.service;

import com.varc.brewnetapp.domain.statistics.query.dto.MyWaitApprovalDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.SafeStockStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.newOrderDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StatisticsService {

    OrderStatisticsDTO findOrderStatistics(String startDate, String endDate);

    List<OrderCountPriceDTO> findOrderCountPriceStatistics(String yearMonth);

    Page<SafeStockStatisticsDTO> findSafeStock(Pageable page);

    Page<newOrderDTO> findNewOrder(Pageable page);

    Page<MyWaitApprovalDTO> findMyWaitApproval(Pageable page, String accessToken);
}
