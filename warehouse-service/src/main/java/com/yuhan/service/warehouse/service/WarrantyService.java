package com.yuhan.service.warehouse.service;

import com.yuhan.service.warranty.model.ItemWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author yuhan
 * @date 12.11.2020 - 13:41
 * @purpose
 */
@FeignClient(value = "warranty-service", url = "localhost:8180")
public interface WarrantyService {

    @RequestMapping(value = "/api/v1/warranty/{itemUid}/warranty", method = RequestMethod.POST)
    public OrderWarrantyResponse warrantyRequest(@PathVariable int itemUid, @Valid @RequestBody ItemWarrantyRequest request);
}
