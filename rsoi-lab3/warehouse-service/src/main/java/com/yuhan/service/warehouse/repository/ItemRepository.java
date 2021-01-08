package com.yuhan.service.warehouse.repository;

import com.yuhan.service.warehouse.domain.Item;
import com.yuhan.service.warehouse.model.SizeChart;
import org.omg.CORBA.IRObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 12.11.2020 - 12:41
 * @purpose
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query(value = "select oi.item from OrderItem oi where oi.orderItemUid = :orderItemUid")
    public Optional<Item> findByOrderItemUid(int orderItemUid);

    @Query(value = "select i from Item i where i.model = :model and i.size = :size")
    public Optional<Item> findItemByModelAndSize(@Param(value = "model") String model, @Param("size") SizeChart size);

    @Modifying
    @Query(value = "update Item set availableCount = availableCount - 1 where id = :id")
    public void takeOneItem(@Param("id") int id);

    @Modifying
    @Query(value = "update Item set availableCount = availableCount + 1" +
            "where id = (select id from OrderItem oi where oi.orderItemUid = :orderItemUid)")
    public void returnOneItem(@Param("orderItemUid") int orderItemUid);
}
