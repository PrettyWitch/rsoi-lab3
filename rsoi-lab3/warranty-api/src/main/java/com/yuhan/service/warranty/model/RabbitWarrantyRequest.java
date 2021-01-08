package com.yuhan.service.warranty.model;

import java.io.Serializable;

/**
 * @author yuhan
 * @date 03.01.2021 - 17:47
 * @purpose
 */
public class RabbitWarrantyRequest implements Serializable {
    private int orderItemUid;
    private ItemWarrantyRequest itemWarrantyRequest;

    public RabbitWarrantyRequest(int orderItemUid, ItemWarrantyRequest itemWarrantyRequest) {
        this.orderItemUid = orderItemUid;
        this.itemWarrantyRequest = itemWarrantyRequest;
    }

    public int getOrderItemUid() {
        return orderItemUid;
    }

    public void setOrderItemUid(int orderItemUid) {
        this.orderItemUid = orderItemUid;
    }

    public ItemWarrantyRequest getItemWarrantyRequest() {
        return itemWarrantyRequest;
    }

    public void setItemWarrantyRequest(ItemWarrantyRequest itemWarrantyRequest) {
        this.itemWarrantyRequest = itemWarrantyRequest;
    }

    @Override
    public String toString() {
        return "RabbitWarrantyRequest{" +
                "orderItemUid=" + orderItemUid +
                ", itemWarrantyRequest=" + itemWarrantyRequest +
                '}';
    }
}
