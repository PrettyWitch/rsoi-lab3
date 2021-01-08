package com.yuhan.service.order.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author yuhan
 * @date 06.11.2020 - 12:24
 * @purpose
 */
public class CreateOrderRequest {
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

