package com.yuhan.service.store.service;

import com.yuhan.service.order.model.CreateOrderRequest;
import com.yuhan.service.order.model.CreateOrderResponse;
import com.yuhan.service.order.model.OrderInfoResponse;
import com.yuhan.service.order.model.OrdersInfoResponse;
import com.yuhan.service.store.model.PurchaseRequest;
import com.yuhan.service.store.model.WarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:05
 * @purpose 对订单增删改查
 */
@FeignClient(value = "order-service", url = "localhost:8380",fallback = OrderServiceFallBack.class)
public interface OrderService {
    //查找订单信息
    @RequestMapping(value = "/api/v1/orders/{userUid}/{orderUid}", method = RequestMethod.GET)
    public Optional<OrderInfoResponse> getOrderInfo(@PathVariable int userUid, @PathVariable int orderUid);

    //查找多个订单信息
    @RequestMapping(value = "/api/v1/orders/{userUid}", method = RequestMethod.GET)
    public Optional<OrdersInfoResponse> getOrderInfoByUser(@PathVariable int userUid);

    //创建订单
    @Transactional
    @RequestMapping(value = "/api/v1/orders/{userUid}", method = RequestMethod.POST)
    public CreateOrderResponse makePurchase(@PathVariable int userUid, @Valid @RequestBody CreateOrderRequest request);

    //退单
    @Transactional
    @RequestMapping(value = "/api/v1/orders/{orderUid}", method = RequestMethod.DELETE)
    public void refundPurchase(@PathVariable int orderUid);

    //保修 @Valid @RequestBody OrderWarrantyRequest request
    @RequestMapping(value = "/api/v1/orders/{orderUid}/warranty", method = RequestMethod.POST)
    public RabbitWarrantyRequest warrantyRequest(@PathVariable int orderUid, @Valid @RequestBody OrderWarrantyRequest request);
}
