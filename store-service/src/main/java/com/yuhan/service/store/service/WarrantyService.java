package com.yuhan.service.store.service;

import com.yuhan.service.warranty.model.WarrantyInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 21:20
 * @purpose
 */
@FeignClient(value = "warranty-service", url = "localhost:8180", fallback = WarrantyServiceFallBack.class)
public interface WarrantyService {

    @RequestMapping(value = "/api/v1/warranty/{itemUid}", method = RequestMethod.GET)
    public Optional<WarrantyInfoResponse> getItemWarrantyInfo(@PathVariable int itemUid);
}
