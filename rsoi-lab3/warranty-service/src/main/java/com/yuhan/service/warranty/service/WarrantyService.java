package com.yuhan.service.warranty.service;

import com.yuhan.service.warranty.domain.Warranty;
import com.yuhan.service.warranty.model.ItemWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.WarrantyInfoResponse;

import java.util.UUID;

/**
 * @author yuhan
 * @date 13.11.2020 - 15:29
 * @purpose
 */
public interface WarrantyService {
    public Warranty getWarrantyByItemUid(int itemUid);

    public WarrantyInfoResponse getWarrantyInfo(int itemUid);

    public OrderWarrantyResponse warrantyRequest(int itemUid, ItemWarrantyRequest request);

    public void startWarranty(int itemUid);

    public void stopWarranty(int itemUid);
}
