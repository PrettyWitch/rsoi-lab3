package com.yuhan.service.order.service;

import com.yuhan.service.order.model.OrderInfoResponse;
import com.yuhan.service.order.model.OrdersInfoResponse;
import com.yuhan.service.order.domain.Order;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:08
 * @purpose 增删改查
 */
public interface OrderService {
    public Order getOrderByUid(int orderUid);

    public OrderInfoResponse getUserOrder(int userUid, int orderUid);

    public OrdersInfoResponse getUserOrders(int userUid);

    public void createOrder(int orderUid, int userUid, int itemUid);

    public void cancelOrder(int orderUid);

    public Boolean checkOrder(int orderUid);
}
