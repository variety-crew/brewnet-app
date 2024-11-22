package com.varc.brewnetapp.domain.delivery.query.mapper;

import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDetailDTO;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {

    List<DeliveryDTO> selectOrderDeliveryList(long offset, long pageSize);

    int selectOrderDeliveryListCnt();

    List<DeliveryDTO> selectPickUpDeliveryList(long offset, long pageSize);

    int selectPickUpDeliveryListCnt();

    DeliveryDetailDTO selectOrderDelivery(int code);

    DeliveryDetailDTO selectExchangeDelivery(int code);

    DeliveryDetailDTO selectReturnDelivery(int code);

    Optional<DeliveryDetailDTO> selectMyDeliveryDetail(String accessToken);
}
