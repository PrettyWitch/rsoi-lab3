package com.yuhan.service.warehouse.model;

import com.yuhan.service.order.model.SizeChart;

import java.util.function.Function;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:13
 * @purpose
 */
public class ItemInfoResponse {
    private String model;
    private String size;
    private int availableCount;
//    private SizeChart size;


    public ItemInfoResponse(String model, String size, int availableCount) {
        this.model = model;
        this.size = size;
        this.availableCount = availableCount;
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

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }
}
