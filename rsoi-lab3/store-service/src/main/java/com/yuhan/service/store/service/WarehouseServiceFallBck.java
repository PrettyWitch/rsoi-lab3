package com.yuhan.service.store.service;

import com.yuhan.service.warehouse.model.ItemInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yuhan
 * @date 27.12.2020 - 14:14
 * @purpose
 */
@Component
public class WarehouseServiceFallBck implements WarehouseService{
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    @Override
    public Optional<ItemInfoResponse> getItemInfo(int orderItemUid){
        ItemInfoResponse i = new ItemInfoResponse("Fall Back", "L", 0);
        logger.info("FallBack: WarehouseService.getItemInfo");
        return Optional.of(i);
    }
}
