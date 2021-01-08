package com.yuhan.service.store.service;

import com.yuhan.service.order.model.CreateOrderRequest;
import com.yuhan.service.store.model.*;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;

import java.util.List;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:24
 * @purpose
 */
public interface StoreService {
    public List<UserOrderResponse> findUserOrders(int userUid);

    public UserOrderResponse findUserOrder(int userUid, int orderUid);

    public int makePurchase(int userUid, CreateOrderRequest request);

    public void refundPurchase(int userUid, int orderUid);

    public RabbitWarrantyRequest warrantyRequest1(int userUid, int orderUid, OrderWarrantyRequest request);
}
