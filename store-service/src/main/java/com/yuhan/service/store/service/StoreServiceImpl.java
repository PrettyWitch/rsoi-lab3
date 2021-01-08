package com.yuhan.service.store.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuhan.service.order.model.CreateOrderRequest;
import com.yuhan.service.order.model.CreateOrderResponse;
import com.yuhan.service.order.model.OrderInfoResponse;
import com.yuhan.service.order.model.OrdersInfoResponse;
import com.yuhan.service.store.exceptions.OrderProcessException;
import com.yuhan.service.store.exceptions.WarrantyProcessException;
import com.yuhan.service.store.model.*;
import com.yuhan.service.warehouse.model.ItemInfoResponse;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import com.yuhan.service.warranty.model.WarrantyInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Function;

/**
 * @author yuhan
 * @date 10.11.2020 - 21:26
 * @purpose
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    UserService userService;
    @Qualifier("com.yuhan.service.store.service.WarehouseService")
    @Autowired
    WarehouseService warehouseService;
    @Qualifier("com.yuhan.service.store.service.OrderService")
    @Autowired
    OrderService orderService;
    @Qualifier("com.yuhan.service.store.service.WarrantyService")
    @Autowired
    WarrantyService warrantyService;

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    //根据用户ID找到用户的所有订单
    @HystrixCommand
    @Override
    public List<UserOrderResponse> findUserOrders(int userUid) {
        if (!userService.checkUserExists(userUid)) {
            throw new EntityNotFoundException("User" + userUid + "not found");
        }
        //找用户的订单
        List<UserOrderResponse> orders = new ArrayList<>();
        logger.info("Request to Order Service for user '{}' orders", userUid);
        Optional<OrdersInfoResponse> userOrders = orderService.getOrderInfoByUser(userUid);

        if (userOrders.isPresent()) {
            logger.info("User {} has {} orders", userUid, userOrders.get().size());

            //将TODO转换为批处理操作
            for (OrderInfoResponse orderInfo : userOrders.get()) {
                int orderUid = orderInfo.getOrderUid();
                int itemUid = orderInfo.getItemUid();

                //处理订单
                logger.info("Processing user {} order {}", userUid, orderUid);
                UserOrderResponse order = new UserOrderResponse(orderUid, orderInfo.getOrderDate());
                //查找订单的仓库信息（Model，size）
                logger.info("Request to Warehouse for item {} info by order {}", itemUid, orderUid);
                Optional<ItemInfoResponse> itemInfo = warehouseService.getItemInfo(itemUid);
                if (itemInfo.isPresent()) {
                    order.setModel(itemInfo.get().getModel());
                    order.setSize(SizeChart.valueOf(itemInfo.get().getSize()));
                }
                //查找订单的保修信息（WarrantyDate,WarrantyStatus）
                logger.info("Request to Warranty for item {} info by order {}", itemUid, orderUid);
                Optional<WarrantyInfoResponse> itemWarrantyInfo = warrantyService.getItemWarrantyInfo(itemUid);
                if (itemWarrantyInfo.isPresent()) {
                    order.setWarrantyDate(itemWarrantyInfo.get().getWarrantyDate());
                    order.setWarrantyStatus(WarrantyStatus.valueOf(itemWarrantyInfo.get().getStatus()));
                }
                orders.add(order);
            }
        } else {
            logger.warn("User {} has no orders", userUid);
        }
        return orders;
    }

    @HystrixCommand
    @Override
    public UserOrderResponse findUserOrder(int userUid, int orderUid) {
        if (!userService.checkUserExists(userUid)) {
            throw new EntityNotFoundException("User" + userUid + "not found");
        }

        logger.info("Request to Order Service for user '{}' order {}", userUid, orderUid);
        OrderInfoResponse orderInfo = orderService.getOrderInfo(userUid, orderUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order %s not found for user %s", orderUid, userUid)));

        UserOrderResponse orderResponse = new UserOrderResponse(orderUid, orderInfo.getOrderDate());
        //处理订单
        logger.info("Processing user {} order {}", userUid, orderUid);
        int itemUid = orderInfo.getItemUid();

        logger.info("Request to Warehouse for item {} info by order {}", itemUid, orderUid);
        Optional<ItemInfoResponse> itemInfo = warehouseService.getItemInfo(itemUid);
        if (itemInfo.isPresent()) {
            orderResponse.setModel(itemInfo.get().getModel());
            orderResponse.setSize(SizeChart.valueOf(itemInfo.get().getSize()));
        }

        logger.info("Request to Warranty for item {} info by order {}", itemUid, orderUid);
        Optional<WarrantyInfoResponse> itemWarrantyInfo = warrantyService.getItemWarrantyInfo(itemUid);
        if (itemWarrantyInfo.isPresent()) {
            orderResponse.setWarrantyDate(itemWarrantyInfo.get().getWarrantyDate());
            orderResponse.setWarrantyStatus(WarrantyStatus.valueOf(itemWarrantyInfo.get().getStatus()));
        }

        return orderResponse;
    }

    //创建订单，返回订单号
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int makePurchase(int userUid, CreateOrderRequest request) {
        logger.info("Process user {} purchase request (model: {}, size: {})", userUid, request.getModel(), request.getSize());
        if (!userService.checkUserExists(userUid)) {
            throw new EntityNotFoundException("User" + userUid + "not found");
        }

        logger.info("Request to Order Service for user {} to process order", userUid);

        try{
            CreateOrderResponse createOrderResponse = orderService.makePurchase(userUid, request);

            logger.info("Order create {}", createOrderResponse);

            return createOrderResponse.getOrderUid();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("Transactional {}", userUid);
            return 0;
        }
    }

    //退货
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refundPurchase(int userUid, int orderUid) {
        logger.info("Process user {} return request for order {}", userUid, orderUid);
        if (!userService.checkUserExists(userUid)) {
            throw new EntityNotFoundException("User" + userUid + "not found");
        }

        logger.info("Request to Order Service for user {} to cancel order {}", userUid, orderUid);
        try {
            orderService.refundPurchase(orderUid);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("Transactional {}", userUid);
        }
    }

    //保修请求，返回保修信息
    @Transactional
    @Override
    public RabbitWarrantyRequest warrantyRequest1(int userUid, int orderUid, OrderWarrantyRequest request) {
        logger.info("Process user {} warranty request for order {}", userUid, orderUid);
        if (!userService.checkUserExists(userUid)) {
            throw new EntityNotFoundException("User" + userUid + "not found");
        }

        logger.info("Request to Order Service for user {} and order {} to make warranty request {}", userUid, orderUid, request.getReason());
        RabbitWarrantyRequest rabbitWarrantyRequest = orderService.warrantyRequest(orderUid, request);
        return rabbitWarrantyRequest;
    }

//    private Function buildWarrantyResponse(int orderUid, OrderWarrantyResponse response) {
//        WarrantyResponse warrantyResponse = new WarrantyResponse(orderUid, response.getWarrantyDate(), response.getDecision());
//        return Objects::nonNull;
//    }
}
