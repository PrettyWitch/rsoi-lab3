package com.yuhan.service.warehouse.model;


/**
 * @author yuhan
 * @date 07.11.2020 - 13:14
 * @purpose
 */
public class OrderItemRequest {
    private int orderUid;
    //    @NotEmpty(message ="{field.is.empty}")
    private String model;
    private String size;

    public OrderItemRequest(int orderUid, String model, String size) {
        this.orderUid = orderUid;
        this.model = model;
        this.size = size;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
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

    @Override
    public String toString() {
        return "OrderItemRequest{" +
                "orderUid=" + orderUid +
                ", model='" + model + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
