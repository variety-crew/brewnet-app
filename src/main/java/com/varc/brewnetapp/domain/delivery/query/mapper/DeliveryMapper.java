package com.varc.brewnetapp.domain.delivery.query.mapper;

import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {

    List<DeliveryDTO> selectOrderDeliveryList(long offset, long pageSize);

    int selectOrderDeliveryListCnt();

    List<DeliveryDTO> selectPickUpDeliveryList(long offset, long pageSize);

    int selectPickUpDeliveryListCnt();
}
