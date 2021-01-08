package com.yuhan.service.warranty.domain;


import com.yuhan.service.warranty.model.WarrantyStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author yuhan
 * @date 13.11.2020 - 15:15
 * @purpose
 */
@Entity
@Table(name = "warranty", indexes = {@Index(name = "idx_warranty_item_uid", columnList = "item_uid", unique = true)})
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "item_uid", nullable = false, unique = true, length = 40)
    private int itemUid;

    @Column(name = "warrany_date", nullable = false)
    private LocalDateTime warrantyDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyStatus status;

    @Column(length = 1024)
    private String comment;


    public Warranty(int itemUid, LocalDateTime warrantyDate, WarrantyStatus status) {
        this.itemUid = itemUid;
        this.warrantyDate = warrantyDate;
        this.status = status;
    }

    public Warranty() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warranty warranty = (Warranty) o;
        return Objects.equals(itemUid, warranty.itemUid) &&
                status == warranty.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemUid, warrantyDate, status);
    }

    @Override
    public String toString() {
        return "Warranty{" +
                "id=" + id +
                ", itemUid=" + itemUid +
                ", warrantyDate=" + warrantyDate +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemUid() {
        return itemUid;
    }

    public void setItemUid(int itemUid) {
        this.itemUid = itemUid;
    }

    public LocalDateTime getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(LocalDateTime warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public WarrantyStatus getStatus() {
        return status;
    }

    public void setStatus(WarrantyStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
