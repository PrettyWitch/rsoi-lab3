package com.yuhan.service.order.service;

import com.yuhan.service.order.model.SizeChart;
import com.yuhan.service.warehouse.model.OrderItemRequest;
import com.yuhan.service.warehouse.model.OrderItemResponse;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:08
 * @purpose 仓库服务
 */
@FeignClient(value = "warehouse-service", url = "localhost:8280")
public interface WarehouseService {
    //取货
    @Transactional
    @RequestMapping(value = "/api/v1/warehouse", method = RequestMethod.POST)
    public Optional<OrderItemResponse> takeItem(@Valid @RequestBody OrderItemRequest request);

    //退货
    @Transactional
    @RequestMapping(value = "/api/v1/warehouse/{orderItemUid}", method = RequestMethod.DELETE)
    public void returnItem(@PathVariable int orderItemUid);

    //使用保修
    @RequestMapping(value = "/api/v1/warehouse/{orderItemUid}/warranty", method = RequestMethod.POST)
    public RabbitWarrantyRequest useWarrantyItem(@PathVariable int orderItemUid, @Valid @RequestBody OrderWarrantyRequest request);
}
