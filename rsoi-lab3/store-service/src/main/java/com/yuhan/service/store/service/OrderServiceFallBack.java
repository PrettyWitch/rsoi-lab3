package com.yuhan.service.store.service;

import com.yuhan.service.order.model.*;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
//import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author yuhan
 * @date 27.12.2020 - 14:13
 * @purpose
 */
@Component
public class OrderServiceFallBack implements OrderService{
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    @Override
    public Optional<OrderInfoResponse> getOrderInfo(int userUid, int orderUid) {
        PaymentStatus status = PaymentStatus.valueOf("WAITING");
        OrderInfoResponse o = new OrderInfoResponse(orderUid,"Fall Back",0, status);
        logger.info("FallBack: OrderService.getOrderInfo");
        return Optional.of(o);

    }

    @Override
    public Optional<OrdersInfoResponse> getOrderInfoByUser(int userUid) {
        PaymentStatus status = PaymentStatus.valueOf("WAITING");
        OrdersInfoResponse o = new OrdersInfoResponse();
        logger.info("FallBack: OrderService.getOrderInfoByUser");
        return Optional.of(o);
//        return Optional.empty();
    }

    @Override
    public CreateOrderResponse makePurchase(int userUid, @Valid CreateOrderRequest request) {
        return null;
    }

    @Override
    public void refundPurchase(int orderUid) {

    }

    @Override
    public RabbitWarrantyRequest warrantyRequest(int orderUid, @Valid OrderWarrantyRequest request) {
        return null;
    }


}
