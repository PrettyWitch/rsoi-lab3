package com.yuhan.service.warehouse.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author yuhan
 * @date 11.11.2020 - 15:57
 * @purpose
 */
@Entity
@Table(name = "order_item", indexes = {
        @Index(name = "idx_order_item_item_id", columnList = "item_id"),
        @Index(name = "idx_order_item_order_uid", columnList = "order_uid"),
        @Index(name = "idx_order_item_item_uid", columnList = "order_item_uid", unique = true)
})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_uid", nullable = false, length = 40)
    private int orderUid;

    @Column(name = "order_item_uid", nullable = false, length = 40, unique = true)
    private int orderItemUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "fk_order_item_item_id"))
    private Item item;

    @Column
    private Boolean canceled = false;

    public OrderItem(int orderUid, int orderItemUid, Item item, Boolean canceled) {
        this.orderUid = orderUid;
        this.orderItemUid = orderItemUid;
        this.item = item;
        this.canceled = canceled;
    }

    public OrderItem() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(orderUid, orderItem.orderUid) &&
                Objects.equals(orderItemUid, orderItem.orderItemUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderUid, orderItemUid);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderUid=" + orderUid +
                ", orderItemUid=" + orderItemUid +
                ", item=" + item +
                ", canceled=" + canceled +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }

    public int getOrderItemUid() {
        return orderItemUid;
    }

    public void setOrderItemUid(int orderItemUid) {
        this.orderItemUid = orderItemUid;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }
}
