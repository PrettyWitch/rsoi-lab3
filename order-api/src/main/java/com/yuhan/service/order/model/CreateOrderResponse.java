package com.yuhan.service.order.model;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 12:55
 * @purpose
 */
public class CreateOrderResponse {
    private int orderUid;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(int orderUid) {
        this.orderUid = orderUid;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }
}
