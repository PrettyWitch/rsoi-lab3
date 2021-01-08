package com.yuhan.service.store.model;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:10
 * @purpose
 */
public class UserOrderResponse {
    private int orderUid;
    private String date;
    private String model;
    private SizeChart size;
    private String warrantyDate;
    private WarrantyStatus warrantyStatus;

    public UserOrderResponse(int orderUid, String date, String model, SizeChart size, String warrantyDate, WarrantyStatus warrantyStatus) {
        this.orderUid = orderUid;
        this.date = date;
        this.model = model;
        this.size = size;
        this.warrantyDate = warrantyDate;
        this.warrantyStatus = warrantyStatus;
    }

    public UserOrderResponse(int orderUid, String date) {
        this.orderUid = orderUid;
        this.date = date;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public SizeChart getSize() {
        return size;
    }

    public void setSize(SizeChart size) {
        this.size = size;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public WarrantyStatus getWarrantyStatus() {
        return warrantyStatus;
    }

    public void setWarrantyStatus(WarrantyStatus warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }
}
