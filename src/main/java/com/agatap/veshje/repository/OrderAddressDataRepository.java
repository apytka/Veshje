package com.agatap.veshje.repository;

import com.agatap.veshje.controller.DTO.OrderAddressDataDTO;
import com.agatap.veshje.model.OrderAddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderAddressDataRepository extends JpaRepository<OrderAddressData, Integer> {

    Optional<OrderAddressData> findByOrderId(Integer orderId);
}
