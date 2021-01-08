package com.yuhan.service.store.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:10
 * @purpose
 */
public class PurchaseRequest {
    @NotEmpty(message = "{field.is.empty}")
    private String model;
    private SizeChart size;

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
}
