package com.yuhan.service.warranty.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:18
 * @purpose
 */
public class OrderWarrantyRequest {
    @NotEmpty(message = "{field.is.empty}")
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
