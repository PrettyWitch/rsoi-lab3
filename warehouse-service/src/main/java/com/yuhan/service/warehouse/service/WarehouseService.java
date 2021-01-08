package com.yuhan.service.warehouse.service;

import com.yuhan.service.warehouse.domain.Item;
import com.yuhan.service.warehouse.model.ItemInfoResponse;
import com.yuhan.service.warehouse.model.OrderItemRequest;
import com.yuhan.service.warehouse.model.OrderItemResponse;

import java.util.UUID;

/**
 * @author yuhan
 * @date 12.11.2020 - 13:38
 * @purpose
 */
public interface WarehouseService {

    public Item createItem(Item item);

    public ItemInfoResponse getItemInfo(int orderItemUid);

    public Item getOrderItem(int orderItemUid);

    public OrderItemResponse takeItem(OrderItemRequest request);

    public void returnItem(int orderItemUid);

    public Integer checkItemAvailableCount(int orderItemUid);
}
