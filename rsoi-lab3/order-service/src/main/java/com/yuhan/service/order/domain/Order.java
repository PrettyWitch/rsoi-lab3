package com.yuhan.service.order.domain;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:00
 * @purpose
 */

import com.yuhan.service.order.model.PaymentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_user_uid", columnList = "user_uid"),
        @Index(name = "idx_orders_order_uid", columnList = "order_uid", unique = true),
        @Index(name = "idx_orders_user_uid_and_order_uid", columnList = "user_uid, order_uid")
})

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_uid", nullable = false, length = 40)
    private int userUid;

    @Column(name = "order_uid", nullable = false, unique = true, length = 40)
    private int orderUid;

    @Column(name = "item_uid", nullable = false, length = 40)
    private int itemUid;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Order() {
    }

    public Order(int orderUid, LocalDateTime orderDate) {
        this.orderUid = orderUid;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserUid() {
        return userUid;
    }

    public void setUserUid(int userUid) {
        this.userUid = userUid;
    }

    public int getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(int orderUid) {
        this.orderUid = orderUid;
    }

    public int getItemUid() {
        return itemUid;
    }

    public void setItemUid(int itemUid) {
        this.itemUid = itemUid;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderUid, order.orderUid) &&
                Objects.equals(itemUid, order.itemUid) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderUid, itemUid, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userUid=" + userUid +
                ", orderUid=" + orderUid +
                ", itemUid=" + itemUid +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }
}
