package com.yuhan.service.warehouse.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuhan.service.warehouse.domain.Item;
import com.yuhan.service.warehouse.domain.OrderItem;
import com.yuhan.service.warehouse.exceptions.ItemNotAvailableException;
import com.yuhan.service.warehouse.model.ItemInfoResponse;
import com.yuhan.service.warehouse.model.OrderItemRequest;
import com.yuhan.service.warehouse.model.OrderItemResponse;
import com.yuhan.service.warehouse.model.SizeChart;
import com.yuhan.service.warehouse.repository.ItemRepository;
import com.yuhan.service.warehouse.repository.OrderItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author yuhan
 * @date 12.11.2020 - 13:42
 * @purpose
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);


    @Transactional
    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @HystrixCommand
    @Transactional(readOnly = true)
    @Override
    public ItemInfoResponse getItemInfo(int orderItemUid) {
        Optional<Item> byOrderItemUid = itemRepository.findByOrderItemUid(orderItemUid);
        if (byOrderItemUid.isPresent()) {
            byOrderItemUid.orElseThrow(() -> new EntityNotFoundException(String.format("Item not found for orderItemUid %s", orderItemUid)));
        }
        return new ItemInfoResponse(byOrderItemUid.get().getModel(), byOrderItemUid.get().getSize().name(), byOrderItemUid.get().getAvailableCount());
    }

    @HystrixCommand
    @Transactional(readOnly = true)
    @Override
    public Item getOrderItem(int orderItemUid) {
        return itemRepository.findByOrderItemUid(orderItemUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Item not found for orderItemUid %s", orderItemUid)));
    }

    @Transactional
    @Override
    public OrderItemResponse takeItem(OrderItemRequest request) {
        logger.info("Take item {}", request.toString());

        String model = request.getModel();
        logger.info("Take model {}", model);

        SizeChart size = SizeChart.valueOf(request.getSize());
        logger.info("Take size {}", size);
        int orderUid = request.getOrderUid();

        logger.info("Take item (model: {}, sie: {}) for order {}", model, size, orderUid);
        Item item = itemRepository.findItemByModelAndSize(model, size)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Item with model $s and size $s not found", model, size)));
        if (item.getAvailableCount() < 1) {
            throw new ItemNotAvailableException(String.format("Item %s is finished on warehouse", item));
        }

        int orderItemUid = (int) (Math.random() * 100);
        OrderItem orderItem = new OrderItem(orderUid, orderItemUid, item, false);
        orderItem = orderItemRepository.save(orderItem);
        logger.info("Create OrderItem {} for order {}", orderItem, orderUid);

        itemRepository.takeOneItem(item.getId());
        logger.info("Take one item for itemId", item.getId());
        return buildOrderItemResponse(orderItem, item);
    }

    @Override
    @Transactional
    public void returnItem(int orderItemUid) {
        itemRepository.returnOneItem(orderItemUid);
        orderItemRepository.cancelOrderItem(orderItemUid);
        logger.info("Return one item {} to warehouse", orderItemUid);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer checkItemAvailableCount(int orderItemUid) {
        return getOrderItem(orderItemUid).getAvailableCount();
    }

//    private Function<Item,ItemInfoResponse> buildItemInfo(Item item){
//        ItemInfoResponse itemInfoResponse = new ItemInfoResponse(item.getModel(), item.getSize().name());
//        return Function;
//    }

    private OrderItemResponse buildOrderItemResponse(OrderItem orderItem, Item item) {
        OrderItemResponse orderItemResponse
                = new OrderItemResponse(orderItem.getOrderItemUid(), orderItem.getOrderUid(), item.getSize().name(), item.getModel());
        return orderItemResponse;
    }

}
