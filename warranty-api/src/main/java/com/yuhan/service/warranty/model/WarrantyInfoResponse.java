package com.yuhan.service.warranty.model;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:18
 * @purpose
 */
public class WarrantyInfoResponse {
    private int itemUid;
    private String warrantyDate;
    private String status;
//    private WarrantyStatus status;


    public WarrantyInfoResponse(int itemUid, String warrantyDate, String status) {
        this.itemUid = itemUid;
        this.warrantyDate = warrantyDate;
        this.status = status;
    }

    public int getItemUid() {
        return itemUid;
    }

    public void setItemUid(int itemUid) {
        this.itemUid = itemUid;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
