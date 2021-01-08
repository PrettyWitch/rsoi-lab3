package com.yuhan.service.warranty.model;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:18
 * @purpose
 */
public class OrderWarrantyResponse {
    private String warrantyDate;
    private String decision;
    //private WarrantyDecision decision;


    public OrderWarrantyResponse(String warrantyDate, String decision) {
        this.warrantyDate = warrantyDate;
        this.decision = decision;
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

    @Override
    public String toString() {
        return "OrderWarrantyResponse{" +
                "warrantyDate='" + warrantyDate + '\'' +
                ", decision='" + decision + '\'' +
                '}';
    }
}
