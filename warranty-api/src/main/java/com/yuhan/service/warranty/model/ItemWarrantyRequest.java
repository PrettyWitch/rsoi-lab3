package com.yuhan.service.warranty.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:17
 * @purpose
 */
public class ItemWarrantyRequest {
    @NotEmpty(message = "{field.is.empty}")
    private String reason;
    private int availableCount;

    public ItemWarrantyRequest(String reason, int availableCount) {
        this.reason = reason;
        this.availableCount = availableCount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }
}
