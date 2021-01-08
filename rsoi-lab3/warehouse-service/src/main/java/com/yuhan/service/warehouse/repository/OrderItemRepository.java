package com.yuhan.service.warehouse.repository;

import com.yuhan.service.warehouse.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

/**
 * @author yuhan
 * @date 12.11.2020 - 13:36
 * @purpose
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Modifying
    @Query("update OrderItem set canceled = true where orderItemUid = :orderItemUid")
    public void cancelOrderItem(@Param("orderItemUid") int orderItemUid);
}
