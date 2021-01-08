package com.yuhan.service.order.service;

import com.yuhan.service.order.model.CreateOrderRequest;
import com.yuhan.service.order.model.CreateOrderResponse;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:07
 * @purpose
 */
public interface OrderManagementService {
    public CreateOrderResponse makeOrder(int userUid, CreateOrderRequest request);

    public void refundOrder(int orderUid);

    public RabbitWarrantyRequest useWarranty(int orderUid, OrderWarrantyRequest request);
}
