package com.yuhan.service.warehouse.domain;

import com.yuhan.service.warehouse.model.SizeChart;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author yuhan
 * @date 11.11.2020 - 15:52
 * @purpose
 */
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SizeChart size;

    @Column(name = "available_count", nullable = false)
    private int availableCount = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(model, item.model) &&
                size == item.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, size);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", size=" + size +
                ", availableCount=" + availableCount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public SizeChart getSize() {
        return size;
    }

    public void setSize(SizeChart size) {
        this.size = size;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }
}
