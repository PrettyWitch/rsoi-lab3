package com.yuhan.service.warranty.repository;

import com.yuhan.service.warranty.domain.Warranty;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * @author yuhan
 * @date 13.11.2020 - 15:25
 * @purpose
 */
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

    public Optional<Warranty> findByItemUid(int itemUid);

    @Modifying
    @Query(value = "delete from Warranty w where w.itemUid = :itemUid")
    public Integer stopWarranty(@Param("itemUid") int itemUid);
}
