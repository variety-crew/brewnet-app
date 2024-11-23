package com.varc.brewnetapp.domain.delivery.command.application.service;

import com.varc.brewnetapp.common.domain.order.OrderHistoryStatus;
import com.varc.brewnetapp.domain.delivery.command.application.dto.CreateDeliveryStatusRequestDTO;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryKind;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.DeliveryStatus;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryExchangeStatusHistory;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryExchangeStatusHistory.ExchangeStatus;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryOrderStatusHistory;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryReturnStatusHistory;
import com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity.DeliveryReturnStatusHistory.ReturnStatus;
import com.varc.brewnetapp.domain.delivery.command.domain.repository.DeliveryExchangeStatusHistoryRepository;
import com.varc.brewnetapp.domain.delivery.command.domain.repository.DeliveryOrderStatusHistoryRepository;
import com.varc.brewnetapp.domain.delivery.command.domain.repository.DeliveryReturnStatusHistoryRepository;
import com.varc.brewnetapp.domain.order.command.domain.aggregate.entity.OrderStatusHistory;
import com.varc.brewnetapp.domain.order.command.domain.repository.OrderStatusHistoryRepository;
import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.exception.InvalidDataException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "commandDeliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryOrderStatusHistoryRepository deliveryOrderStatusHistoryRepository;
    private final DeliveryExchangeStatusHistoryRepository deliveryExchangeStatusHistoryRepository;
    private final DeliveryReturnStatusHistoryRepository deliveryReturnStatusHistoryRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryOrderStatusHistoryRepository deliveryOrderStatusHistoryRepository,
        DeliveryExchangeStatusHistoryRepository deliveryExchangeStatusHistoryRepository,
        DeliveryReturnStatusHistoryRepository deliveryReturnStatusHistoryRepository) {
        this.deliveryOrderStatusHistoryRepository = deliveryOrderStatusHistoryRepository;
        this.deliveryExchangeStatusHistoryRepository = deliveryExchangeStatusHistoryRepository;
        this.deliveryReturnStatusHistoryRepository = deliveryReturnStatusHistoryRepository;
    }

    @Transactional
    @Override
    public void createDeliveryStatus(
        CreateDeliveryStatusRequestDTO createDeliveryStatusRequestDTO) {

        if(createDeliveryStatusRequestDTO.getDeliveryKind().equals(DeliveryKind.ORDER)){
            DeliveryOrderStatusHistory.OrderStatus status = null;

            if(createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.SHIPPED)))
                status = DeliveryOrderStatusHistory.OrderStatus.SHIPPED;
            else if (createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.SHIPPING)))
                status = DeliveryOrderStatusHistory.OrderStatus.SHIPPING;
            else 
                throw new InvalidDataException("잘못된 상태값을 입력하셨습니다");

            DeliveryOrderStatusHistory existDeliveryOrderStatusHistory
                = deliveryOrderStatusHistoryRepository.findByOrderCodeAndStatus(
                createDeliveryStatusRequestDTO.getCode(), status).orElse(null);

            if(existDeliveryOrderStatusHistory != null)
                throw new DuplicateException("이미 주문 내역에 해당 status 값이 존재합니다");

            DeliveryOrderStatusHistory deliveryOrderStatusHistory = DeliveryOrderStatusHistory.builder()
                .createdAt(LocalDateTime.now())
                .status(status)
                .active(true)
                .orderCode(createDeliveryStatusRequestDTO.getCode())
                .build();

            deliveryOrderStatusHistoryRepository.save(deliveryOrderStatusHistory);
        }
        else if(createDeliveryStatusRequestDTO.getDeliveryKind().equals(DeliveryKind.EXCHANGE)){

            ExchangeStatus status = null;

            if(createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.SHIPPED)))
                status = ExchangeStatus.SHIPPED;
            else if (createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.SHIPPING)))
                status = ExchangeStatus.SHIPPING;
            else if (createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.PICKED)))
                status = ExchangeStatus.PICKED;
            else if (createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.PICKING)))
                status = ExchangeStatus.PICKING;
            else
                throw new InvalidDataException("잘못된 상태값을 입력하셨습니다");

            DeliveryExchangeStatusHistory existDeliveryexchangeStatusHistory
                = deliveryExchangeStatusHistoryRepository.findByExchangeCodeAndStatus(
                createDeliveryStatusRequestDTO.getCode(), status).orElse(null);

            if(existDeliveryexchangeStatusHistory != null)
                throw new DuplicateException("이미 교환 내역에 해당 status 값이 존재합니다");

            DeliveryExchangeStatusHistory deliveryExchangeStatusHistory = DeliveryExchangeStatusHistory.builder()
                .createdAt(LocalDateTime.now())
                .status(status)
                .active(true)
                .exchangeCode(createDeliveryStatusRequestDTO.getCode())
                .build();


            deliveryExchangeStatusHistoryRepository.save(deliveryExchangeStatusHistory);
        }
        else if(createDeliveryStatusRequestDTO.getDeliveryKind().equals(DeliveryKind.RETURN)){

            ReturnStatus status = null;

            if (createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.PICKED)))
                status = ReturnStatus.PICKED;
            else if (createDeliveryStatusRequestDTO.getDeliveryStatus().equals((DeliveryStatus.PICKING)))
                status = ReturnStatus.PICKING;
            else
                throw new InvalidDataException("잘못된 상태값을 입력하셨습니다");

            DeliveryReturnStatusHistory existDeliveryReturnStatusHistory
                = deliveryReturnStatusHistoryRepository.findByReturnCodeAndStatus(
                createDeliveryStatusRequestDTO.getCode(), status).orElse(null);

            if(existDeliveryReturnStatusHistory != null)
                throw new DuplicateException("이미 반품 내역에 해당 status 값이 존재합니다");

            DeliveryReturnStatusHistory deliveryReturnStatusHistory = DeliveryReturnStatusHistory.builder()
                .createdAt(LocalDateTime.now())
                .status(status)
                .active(true)
                .returnCode(createDeliveryStatusRequestDTO.getCode())
                .build();


            deliveryReturnStatusHistoryRepository.save(deliveryReturnStatusHistory);
        }


    }
}
