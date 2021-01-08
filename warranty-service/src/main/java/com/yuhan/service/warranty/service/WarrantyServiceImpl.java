package com.yuhan.service.warranty.service;

import com.yuhan.service.warranty.domain.Warranty;
import com.yuhan.service.warranty.model.*;
import com.yuhan.service.warranty.repository.WarrantyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * @author yuhan
 * @date 13.11.2020 - 15:32
 * @purpose
 */
@Service
public class WarrantyServiceImpl implements WarrantyService {

    @Autowired
    WarrantyRepository warrantyRepository;

    private static final Logger logger = LoggerFactory.getLogger(WarrantyServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public Warranty getWarrantyByItemUid(int itemUid) {
        return warrantyRepository.findByItemUid(itemUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Warranty not found for itemUid %s", itemUid)));
    }

    @Transactional(readOnly = true)
    @Override
    public WarrantyInfoResponse getWarrantyInfo(int itemUid) {
        return buildWarrantyInfo(getWarrantyByItemUid(itemUid));
    }

    @Transactional
    @Override
    public OrderWarrantyResponse warrantyRequest(int itemUid, ItemWarrantyRequest request) {
        logger.info("Process warranty request (reason: {}) for item '{}'", request.getReason(), itemUid);

        Warranty warranty = getWarrantyByItemUid(itemUid);
        WarrantyDecision decision = WarrantyDecision.REFUSE;
        if (isActiveWarranty(warranty) && warranty.getStatus() == WarrantyStatus.ON_WARRANTY) {
            if (request.getAvailableCount() > 0) {
                decision = WarrantyDecision.RETURN;
            } else decision = WarrantyDecision.FIXING;
        }
        logger.info("Warranty decision on item '{}' is {} (count: {}, status: {})",
                itemUid, decision, request.getAvailableCount(), warranty.getStatus());

        updateWarranty(warranty, decision, request.getReason());
        return new OrderWarrantyResponse(formatDate(warranty.getWarrantyDate()),decision.name());
    }

    @Transactional
    @Override
    public void startWarranty(int itemUid) {
        Warranty warranty = new Warranty(itemUid, LocalDateTime.now(), WarrantyStatus.ON_WARRANTY);

        warrantyRepository.save(warranty);
        logger.info("Start warranty for item '{}'", itemUid);
    }

    @Transactional
    @Override
    public void stopWarranty(int itemUid) {
        Integer deteled = warrantyRepository.stopWarranty(itemUid);
        if (deteled > 0) {
            logger.info("Remove item '{}' from warranty", itemUid);
        }
    }

    private WarrantyInfoResponse buildWarrantyInfo(Warranty warranty) {
        WarrantyInfoResponse warrantyInfoResponse =
                new WarrantyInfoResponse(warranty.getItemUid(), formatDate(warranty.getWarrantyDate()), warranty.getStatus().name());
        return warrantyInfoResponse;
    }

    private String formatDate(LocalDateTime date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }

    private Boolean isActiveWarranty(Warranty warranty) {
        return warranty.getWarrantyDate().isAfter(LocalDateTime.now().minus(1, ChronoUnit.MONTHS));
    }

    public void updateWarranty(Warranty warranty, WarrantyDecision decision, String reason) {
        warranty.setComment(reason);
        if (decision == WarrantyDecision.REFUSE) {
            warranty.setStatus(WarrantyStatus.REMOVED_FROM_WARRANTY);
        } else warranty.setStatus(WarrantyStatus.USE_WARRANTY);

        warrantyRepository.save(warranty);
        logger.info("Update warranty status {} for itemUid '{}'", warranty.getStatus(), warranty.getItemUid());
    }
}
