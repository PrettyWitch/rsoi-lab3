package com.yuhan.service.store.service;

import com.yuhan.service.warranty.model.WarrantyInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yuhan
 * @date 27.12.2020 - 14:15
 * @purpose
 */
@Component
public class WarrantyServiceFallBack implements WarrantyService{
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    @Override
    public Optional<WarrantyInfoResponse> getItemWarrantyInfo(int itemUid) {
        WarrantyInfoResponse w = new WarrantyInfoResponse(itemUid, "Fall Back", "ON_WARRANTY");
        logger.info("FallBack: WarrantyService.getItemWarrantyInfo");
        return Optional.of(w);
    }
}
