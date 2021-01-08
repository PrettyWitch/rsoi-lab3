package com.yuhan.service.order.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuhan.service.order.model.CreateOrderRequest;
import com.yuhan.service.order.model.CreateOrderResponse;
import com.yuhan.service.order.model.SizeChart;
import com.yuhan.service.order.domain.Order;
import com.yuhan.service.order.exception.WarrantyProcessException;
import com.yuhan.service.warehouse.model.OrderItemRequest;
import com.yuhan.service.warehouse.model.OrderItemResponse;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:21
 * @purpose
 */
@Service
public class OrderManagementServiceImpl implements OrderManagementService {
    @Autowired
    OrderService orderService;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    WarrantyService warrantyService;

    private static final Logger logger = LoggerFactory.getLogger(OrderManagementServiceImpl.class);


    /*创建订单*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateOrderResponse makeOrder(int userUid, CreateOrderRequest request) {
        logger.info("Create order");
        //根据用户ID，产品参数
        String model = request.getModel();
        logger.info("Create model {}", model);
        String size = request.getSize().toString();
        logger.info("Create order (model:{}, sie:{}) for user {}", model, size, userUid);
        //仓库操作
        int orderUid = (int) (Math.random() * 100);
        logger.info("Request to WareHouse to take item (model:{}, size: {}) for order {}", model, size, orderUid);

        OrderItemRequest orderItemRequest = new OrderItemRequest(orderUid, model, size);
        try {
            OrderItemResponse orderItemResponse = warehouseService.takeItem(orderItemRequest)
                    .orElseThrow(() -> new WarrantyProcessException(String.format("Can't take item from Warehouse for user %s", userUid)));
            int orderItemUid = orderItemResponse.getOrderItemUid();
            //保修操作
            logger.info("Request to WarrantyService to start warranty on item {} for user {}", orderItemUid, userUid);
            warrantyService.startWarranty(orderItemUid);
            //创建订单
            logger.info("Request to Create order {}", orderUid);
            orderService.createOrder(orderUid, userUid, orderItemUid);
            //返回订单编号
            return new CreateOrderResponse(orderUid);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("Transactional {}", orderUid);
            return new CreateOrderResponse();
        }
    }

    /*退货*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refundOrder(int orderUid) {
        logger.info("Return order '{}'", orderUid);
        Order order = orderService.getOrderByUid(orderUid);
        //操作仓库
        int itemUid = order.getItemUid();
        logger.info("Request to WareHouse to return item {} for order {}", itemUid, orderUid);
        try{
            warehouseService.returnItem(orderUid);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("Transactional {}", orderUid);
        }
        //取消订单
        orderService.cancelOrder(orderUid);
    }

    /*使用保修*/
    @HystrixCommand
    @Override
    public RabbitWarrantyRequest useWarranty(int orderUid, OrderWarrantyRequest request) {
        logger.info("Check warranty (reason: {}) for order {}", request.getReason(), orderUid);
        Order order = orderService.getOrderByUid(orderUid);
        int itemUid = order.getItemUid();

        logger.info("Request to WarrantyService to use warranty for item {} in order {}", itemUid, orderUid);
        return warehouseService.useWarrantyItem(itemUid, request);
    }
}
