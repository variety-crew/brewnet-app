package com.varc.brewnetapp.domain.statistics.query.service;

import com.varc.brewnetapp.domain.statistics.query.dto.OrderCountPriceDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderItemStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.dto.OrderStatisticsDTO;
import com.varc.brewnetapp.domain.statistics.query.mapper.StatisticsMapper;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.exception.InvalidDataException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "queryStatisticsService")
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsMapper statisticsMapper;

    @Autowired
    public StatisticsServiceImpl(StatisticsMapper statisticsMapper) {
        this.statisticsMapper = statisticsMapper;
    }

    @Override
    @Transactional
    public OrderStatisticsDTO findOrderStatistics(String startDate, String endDate) {

        OrderStatisticsDTO orderStatistics = statisticsMapper.selectOrderStatistics(startDate, endDate);
        List<OrderItemStatisticsDTO> orderItemStatisticsList = statisticsMapper.selectOrderItemStatistics(startDate, endDate);
        int orderItemStatisticsCount = statisticsMapper.selectOrderItemStatisticsCnt(startDate, endDate);

        int otherCount = orderItemStatisticsCount;
        double other = 0;

        if(orderItemStatisticsList != null && orderItemStatisticsList.size() > 0) {
            for(OrderItemStatisticsDTO orderItemStatistics : orderItemStatisticsList) {
                double percentage = roundItemPercent(((double) orderItemStatistics.getItemCount() / orderItemStatisticsCount) * 100);
                orderItemStatistics.setItemPercent(percentage);
                other += percentage;
                otherCount -= orderItemStatistics.getItemCount();
            }
        } else
            throw new EmptyDataException("주문 상품이 없어 통계를 제공할 수 없습니다");

        other = roundItemPercent(100 - other);

        OrderItemStatisticsDTO otherItem = new OrderItemStatisticsDTO("기타", other, otherCount);

        orderItemStatisticsList.add(otherItem);
        orderStatistics.setItems(orderItemStatisticsList);

        return orderStatistics;
    }

    @Override
    @Transactional
    public List<OrderCountPriceDTO> findOrderCountPriceStatistics(String yearMonth) {

        List<OrderCountPriceDTO> orderCountPriceList = statisticsMapper.selectOrderCountPriceList(yearMonth);
        return List.of();
    }

    public double roundItemPercent(double itemPercent) {
        BigDecimal bd = new BigDecimal(itemPercent).setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
