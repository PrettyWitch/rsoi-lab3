package com.yuhan.service.order.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yuhan.service.order.model.OrderInfoResponse;
import com.yuhan.service.order.model.OrdersInfoResponse;
import com.yuhan.service.order.model.PaymentStatus;
import com.yuhan.service.order.domain.Order;
import com.yuhan.service.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:29
 * @purpose
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderManagementServiceImpl.class);

    //查询订单，返回一个订单
    @HystrixCommand
    @Transactional(readOnly = true)
    public Order getOrderByUid(int orderUid) {
        return orderRepository.findByOrderUid(orderUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order %s not found", orderUid)));
    }

    //通过用户和订单号查询订单，返回订单信息
    @HystrixCommand
    @Override
    public OrderInfoResponse getUserOrder(int userUid, int orderUid) {
        Optional<Order> byUserUidAndOrderUid = orderRepository.findByUserUidAndOrderUid(userUid, orderUid);

        if (byUserUidAndOrderUid.isPresent()) {
            byUserUidAndOrderUid.orElseThrow(() -> new EntityNotFoundException(String.format("Not found order %s for user %s ", orderUid, userUid)));
        }
        return new OrderInfoResponse(byUserUidAndOrderUid.get().getOrderUid(), formatDate(byUserUidAndOrderUid.get().getOrderDate()),
                byUserUidAndOrderUid.get().getItemUid(), byUserUidAndOrderUid.get().getStatus());
    }

    //通过用户查询多个订单，返回订单信息 需要把order变成info
    @HystrixCommand
    @Override
    public OrdersInfoResponse getUserOrders(int userUid) {
        List<Order> byUserUid = orderRepository.findByUserUid(userUid);
        return buildOrdersInfo(byUserUid);
    }

    //创建订单
    @Transactional
    public void createOrder(int orderUid, int userUid, int itemUid) {
        Order order = new Order();
        order.setOrderUid(orderUid);
        order.setUserUid(userUid);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(PaymentStatus.PAID);
        order.setItemUid(itemUid);

        orderRepository.save(order);
        logger.debug("Create order {} for user {} and item {}", orderUid, userUid, itemUid);
    }

    //删除订单
    @Transactional
    public void cancelOrder(int orderUid) {
        int deleteOrder = orderRepository.deleteOrder(orderUid);
        if (deleteOrder > 0) {
            logger.debug("Deleted {} order", orderUid);
        }
    }

    //查询订单是否存在
    @Transactional(readOnly = true)
    public Boolean checkOrder(int orderUid) {
        return orderRepository.findByOrderUid(orderUid).isPresent();
    }

    private Function buildOrderInfo(Order order) {
        OrderInfoResponse orderInfoResponse =
                new OrderInfoResponse(order.getOrderUid(), formatDate(order.getOrderDate()), order.getItemUid(), order.getStatus());
        return Objects::nonNull;
    }

    private OrdersInfoResponse buildOrdersInfo(List<Order> orders) {
        OrdersInfoResponse ordersInfoResponse = new OrdersInfoResponse();
        for (Order order : orders) {
            OrderInfoResponse orderInfoResponse =
                    new OrderInfoResponse(order.getOrderUid(), formatDate(order.getOrderDate()), order.getItemUid(), order.getStatus());
            ordersInfoResponse.add(orderInfoResponse);
        }
        return ordersInfoResponse;
    }

    private String formatDate(LocalDateTime date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }

}
