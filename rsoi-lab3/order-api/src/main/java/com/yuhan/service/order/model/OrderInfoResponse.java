package com.yuhan.service.order.model;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 12:56
 * @purpose
 */
public class OrderInfoResponse {
    private int orderUid;
    private String orderDate;
    private int itemUid;
    private PaymentStatus status;

    public OrderInfoResponse(int orderUid, String orderDate, int itemUid, PaymentStatus status) {
        this.orderUid = orderUid;
        this.orderDate = orderDate;
        this.itemUid = itemUid;
        this.status = status;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getItemUid() {
        return itemUid;
    }

    public void setItemUid(int itemUid) {
        this.itemUid = itemUid;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
