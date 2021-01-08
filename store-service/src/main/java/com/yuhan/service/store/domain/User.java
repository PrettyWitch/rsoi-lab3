package com.yuhan.service.store.domain;


import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 14:48
 * @purpose
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_name", columnList = "name", unique = true),
        @Index(name = "idx_user_user_uid", columnList = "user_uid", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "user_uid", nullable = false, length = 40, unique = true)
    private int userUid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(userUid, user.userUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userUid);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userUid=" + userUid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserUid() {
        return userUid;
    }

    public void setUserUid(int userUid) {
        this.userUid = userUid;
    }
}
