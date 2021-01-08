package com.yuhan.service.warehouse.model;

import com.yuhan.service.order.model.SizeChart;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:14
 * @purpose
 */
public class OrderItemResponse {
    private int orderItemUid;
    private int orderUid;
    private String model;
    private String size;
    //private SizeChart size;


    public OrderItemResponse(int orderItemUid, int orderUid, String model, String size) {
        this.orderItemUid = orderItemUid;
        this.orderUid = orderUid;
        this.model = model;
        this.size = size;
    }

    public int getOrderItemUid() {
        return orderItemUid;
    }

    public void setOrderItemUid(int orderItemUid) {
        this.orderItemUid = orderItemUid;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
