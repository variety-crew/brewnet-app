package com.varc.brewnetapp.domain.delivery.query.mapper;

import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.DeliveryDetailDTO;
import com.varc.brewnetapp.domain.delivery.query.dto.ItemDTO;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {

    List<DeliveryDTO> selectOrderDeliveryList(long offset, long pageSize);

    int selectOrderDeliveryListCnt();

    List<DeliveryDTO> selectPickUpDeliveryList(long offset, long pageSize);

    int selectPickUpDeliveryListCnt();

    List<ItemDTO> selectOrderDelivery(int code);

    List<ItemDTO> selectExchangeDelivery(int code);

    List<ItemDTO> selectReturnDelivery(int code);

    Optional<DeliveryDetailDTO> selectMyDeliveryDetail(int deliveryMemberCode);

    List<Integer> selectDeliveryFranchiseMemberCode(int code);

    Optional<DeliveryDetailDTO> selectMyDeliveryDetailV1(int deliveryMemberCode);
}
