package com.yuhan.service.store.service;

import com.yuhan.service.warehouse.model.ItemInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:30
 * @purpose
 */
@FeignClient(value = "warehouse-service", url = "localhost:8280", fallback = WarehouseServiceFallBck.class)
public interface WarehouseService {

    @RequestMapping(value = "/api/v1/warehouse/{orderItemUid}", method = RequestMethod.GET)
    //@PathVariable UUID orderItemUid
    public Optional<ItemInfoResponse> getItemInfo(@PathVariable int orderItemUid);
}
