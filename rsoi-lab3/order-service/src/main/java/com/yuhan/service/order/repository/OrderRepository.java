package com.yuhan.service.order.repository;

import com.yuhan.service.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:06
 * @purpose
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findByOrderUid(int orderUid);

    public Optional<Order> findByUserUidAndOrderUid(int userUid, int orderUid);

    public List<Order> findByUserUid(int userUid);

    @Modifying
    @Query("delete from Order p where p.orderUid = :orderUid")
    public int deleteOrder(@Param("orderUid") int orderUid);
}
