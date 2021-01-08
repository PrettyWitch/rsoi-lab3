package com.yuhan.service.store.model;

import java.util.UUID;
import java.util.function.Function;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:12
 * @purpose
 */
public class WarrantyResponse {
    private int orderUid;
    private String warrantyDate;
    private String decision;

    public WarrantyResponse() {
    }

    public WarrantyResponse(int orderUid, String warrantyDate, String decision) {
        this.orderUid = orderUid;
        this.warrantyDate = warrantyDate;
        this.decision = decision;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
