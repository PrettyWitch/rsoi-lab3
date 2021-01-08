package com.yuhan.service.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:08
 * @purpose
 */
@FeignClient(value = "warranty-service", url = "localhost:8180")
public interface WarrantyService {

    @Transactional
    @RequestMapping(value = "/api/v1/warranty/{itemUid}", method = RequestMethod.POST)
    public void startWarranty(@PathVariable int itemUid);

    @RequestMapping(value = "/api/v1/warranty/{itemUid}", method = RequestMethod.DELETE)
    public void stopWarranty(@PathVariable int itemUid);
}
