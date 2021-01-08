package com.yuhan.service.store.repository;

import com.yuhan.service.store.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:03
 * @purpose 商店服务
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUserUid(int userUid);
}
